/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.markup.html.form;

import java.util.List;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * Renders one choice. Separates the 'id' values used for internal representation from 'display
 * values' which are the values shown to the user of components that use this renderer.
 *
 * Usage:
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers)
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be toString() and the id the index
 * of the object in the ListOfUsers.
 * </p>
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers,
 * 	new ChoiceRenderer&lt;User&gt;(&quot;name&quot;))
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be looked up by property expression
 * ("name") and the id the index of the object in the ListOfUsers
 * </p>
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers,
 * 	new ChoiceRenderer&lt;User&gt;(&quot;name&quot;, &quot;id&quot;))
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be looked up by property expression
 * ("name") and the id will be looked up by the property expression "id"
 * </p>
 * 
 * @author jcompagner
 * 
 * @param <T>
 *            The model object type
 */
public class ChoiceRenderer<T> implements IClusterable
{
	private static final long serialVersionUID = 1L;

	/** expression for getting the display value. */
	private final String displayExpression;

	/** expression for getting the id. */
	private final String idExpression;

	/**
	 * Constructor. 
	 * 
	 * When you use this constructor, the display value will be determined by calling
	 * toString() on the list object, and the id will be based on the list index. the id value will
	 * be the index
	 */
	public ChoiceRenderer()
	{
		this(null);
	}

	/**
	 * Constructor. 
	 * 
	 * When you use this constructor, the display value will be determined by executing
	 * the given property expression on the list object, and the id will be based on the list index.
	 * The display value will be calculated by the given property expression
	 * 
	 * @param displayExpression
	 *            A property expression to get the display value
	 */
	public ChoiceRenderer(String displayExpression)
	{
		this(displayExpression, null);
	}

	/**
	 * Constructor. 
	 * 
	 * When you use this constructor, both the id and the display value will be
	 * determined by executing the given property expressions on the list object.
	 * 
	 * @param displayExpression
	 *            A property expression to get the display value
	 * @param idExpression
	 *            A property expression to get the id value
	 */
	public ChoiceRenderer(String displayExpression, String idExpression)
	{
		this.displayExpression = displayExpression;
		this.idExpression = idExpression;
	}

	/**
	 * Get the value for displaying to an end user.
	 *
	 * @param object
	 *            the actual object
	 * @return the value meant for displaying to an end user
	 */
	public Object getDisplayValue(T object)
	{
		Object returnValue = object;
		if ((displayExpression != null) && (object != null))
		{
			returnValue = PropertyResolver.getValue(displayExpression, object);
		}

		if (returnValue == null)
		{
			return "";
		}

		return returnValue;
	}

	/**
	 * This method is called to get the id value of an object (used as the value attribute of a
	 * choice element) The id can be extracted from the object like a primary key, or if the list is
	 * stable you could just return a toString of the index.
	 * <p>
	 * Note that the given index can be {@code -1} if the object in question is not contained in the
	 * available choices.
	 *
	 * @param object
	 *            The object for which the id should be generated
	 * @param index
	 *            The index of the object in the choices list.
	 * @return String
	 */
	public String getIdValue(T object, int index)
	{
		if (idExpression == null)
		{
			return Integer.toString(index);
		}

		if (object == null)
		{
			return "";
		}

		Object returnValue = PropertyResolver.getValue(idExpression, object);
		if (returnValue == null)
		{
			return "";
		}

		return returnValue.toString();
	}

	/**
	 * This method is called to get an object back from its id representation.
	 *
	 * The {@code id} may be used to find/load the object in a more efficient way
	 * than loading all {@code choices} and find the one with the same id in the list
	 *
	 * @param id
	 *          The id representation of the object
	 * @param choices
	 *          The list of all rendered choices
	 * @return A choice from the list that has this {@code id}
	 */
	public T getObject(String id, IModel<? extends List<? extends T>> choices)
	{
		List<? extends T> _choices = choices.getObject();
		for (int index = 0; index < _choices.size(); index++)
		{
			// Get next choice
			final T choice = _choices.get(index);
			if (getIdValue(choice, index).equals(id))
			{
				return choice;
			}
		}
		return null;
	}
}
