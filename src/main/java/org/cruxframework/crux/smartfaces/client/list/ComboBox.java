/*
 * Copyright 2015 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.smartfaces.client.list;

import org.cruxframework.crux.core.client.collection.Array;
import org.cruxframework.crux.core.client.dataprovider.DataFilter;
import org.cruxframework.crux.core.client.dataprovider.DataProvider;
import org.cruxframework.crux.core.client.utils.StringUtils;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * A Combobox widget that uses a {@link DataProvider} to provide its items collection.
 * 
 * @author wesley.diniz
 *
 * @param <T> The item type
 */
public class ComboBox<T> extends AbstractComboBox<String, T>
{
	/**
	 * Constructor
	 * @param optionsRenderer handler for options rendering 
	 */
	public ComboBox(OptionsRenderer<String, T> optionsRenderer)
	{
		super(optionsRenderer);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler)
	{
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setValue(String value)
	{
		setValue(value, false);
	}

	@Override
	public void setValue(final String value, boolean fireEvents)
	{
		DataProvider<T> dataProvider = getDataProvider();
		Array<T> filterResult = dataProvider.filter(new DataFilter<T>(){
			
			@Override
			public boolean accept(T dataObject)
			{
				return StringUtils.unsafeEquals(optionsRenderer.getValue(dataObject), value);
			}
		});
		
		if(filterResult.size() > 0)
		{
			T obj = filterResult.get(0);
			selectItem(optionsRenderer.getText(obj), optionsRenderer.getValue(obj), dataProvider.indexOf(obj));
		}
	}

	@Override
	protected void setValueByObject(T obj)
	{
		setValue(optionsRenderer.getValue(obj));
	}
}
