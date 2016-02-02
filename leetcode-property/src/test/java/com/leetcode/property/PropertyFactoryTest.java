package com.leetcode.property;

import org.junit.Assert;
import org.junit.Test;

public class PropertyFactoryTest {
	private static final Long nullValue = null;
	private static final Long constValue = 1l;
	private static final Property<Long> nullProperty = PropertyFactory.asProperty(nullValue);
	private static final Property<Long> constProperty = PropertyFactory.asProperty(2l);

	@Test
	public void testAsPropertyWithOneParam() {
		{
			final Property<Long> property = PropertyFactory.asProperty(nullValue);
			Assert.assertEquals(nullValue, property.get());
		}

		{
			final Property<Long> property = PropertyFactory.asProperty(constValue);
			Assert.assertEquals(constValue, property.get());
		}
	}

	@Test
	public void testAsPropertyWithTwoParam() {
		{
			final Property<Long> property = PropertyFactory.asProperty(nullProperty, constValue);
			Assert.assertEquals(constValue, property.get());
		}

		{
			final Property<Long> property = PropertyFactory.asProperty(constProperty, constValue);
			Assert.assertEquals(constProperty.get(), property.get());
		}
	}

	@Test
	public void testAsPropertyWithMultiParam() {
		{
			final Property<Long> property = PropertyFactory.asProperty(PropertyFactory.asProperty(constValue), constProperty, nullProperty);
			Assert.assertEquals(property.get(), constValue);
		}

		{
			final Property<Long> property = PropertyFactory.asProperty(constProperty, PropertyFactory.asProperty(constValue), nullProperty);
			Assert.assertEquals(property.get(), constProperty.get());
		}

		{
			final Property<Long> property = PropertyFactory.asProperty(nullProperty, constProperty, PropertyFactory.asProperty(constValue));
			Assert.assertEquals(property.get(), constProperty.get());
		}
	}

	@Test
	public void testAsDynamicPropertyWithOneParam() {
		{
			final DynamicProperty<Long> property = PropertyFactory.asDynamicProperty(nullValue);
			Assert.assertEquals(nullValue, property.get());
			property.set(constValue);
			Assert.assertEquals(constValue, property.get());
		}

		{
			final DynamicProperty<Long> property = PropertyFactory.asDynamicProperty(constValue);
			Assert.assertEquals(constValue, property.get());
			property.set(nullValue);
			Assert.assertEquals(nullValue, property.get());
		}
	}

	@Test
	public void testAsDynamicPropertyWithTwoParam() {
		{
			final DynamicProperty<Long> property = PropertyFactory.asDynamicProperty(nullValue, constValue);
			Assert.assertEquals(constValue, property.get());
		}

		{
			final DynamicProperty<Long> property = PropertyFactory.asDynamicProperty(constValue, constProperty.get());
			Assert.assertEquals(constValue, property.get());
		}
	}

	@Test
	public void nullPropertyTest() {
		Assert.assertEquals(null, PropertyFactory.nullProperty().get());
	}
}
