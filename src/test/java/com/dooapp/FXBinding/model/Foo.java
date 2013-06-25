/*
 * Copyright (c) 2012, dooApp <contact@dooapp.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of dooApp nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dooapp.FXBinding.model;

import com.dooapp.FXBinding.FXDoubleBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.junit.Assert;
import org.junit.Test;

/**
 * A simple demo class
 * Created at 20/06/13 14:44.<br>
 *
 * @author <a href="mailto:christophe@dooapp.com">Christophe DUFOUR</a>
 */
public class Foo {
    public static final double DELTA = 0.00001d;
    /**
     * A list of Data values
     */
    private ObservableList<XYChart.Data<Number, Number>> values = FXCollections.observableArrayList(new XYChart.Data<Number, Number>(0.3d, 0.3d), new XYChart.Data<Number, Number>(0.1d, 0.1d), new XYChart.Data<Number, Number>(0.2d, 0.2d));
    /**
     * A list of Data values
     */
    public ObservableList<XYChart.Data<Number, Number>> values() {
        return values;
    }
    /**
     * the average value of all XValue of Data in values
     *
     * @see #values()
     */
    private DoubleBinding averageBinding = new FXDoubleBinding() {
        /**
         * This method is called every time the binding is becoming invalid.
         */
        @Override
        protected void configure() {
            super.bind(values());
            for (XYChart.Data<Number, Number> d : values()) {
                super.bind(d.XValueProperty());
            }
        }
        @Override
        protected double compute() {
            double result = 0;
            if (values().isEmpty()) {
                return result;
            }
            for (XYChart.Data<Number, Number> d : values()) {
                result += d.getXValue().doubleValue();
            }
            return result / values().size();
        }
    };
    /**
     * the average value of all double in values
     *
     * @see #values()
     */
    public DoubleBinding averageBinding() {
        return averageBinding;
    }
    /**
     * Just a test to show a demo
     */
    @Test
    public void test1() {
        Foo foo = new Foo();
        System.out.println(foo.averageBinding().get()); //Should print 0.2
        Assert.assertEquals(foo.averageBinding().get(), 0.2d, DELTA);
        foo.values().add(new XYChart.Data<Number, Number>(-3d, -3d));
        System.out.println(foo.averageBinding().get()); //Should print -0.6
        Assert.assertEquals(foo.averageBinding().get(), -0.6, DELTA);
        foo.values().get(0).setXValue(30d);
        System.out.println(foo.averageBinding().get()); //Should  print 6.825
        Assert.assertEquals(foo.averageBinding().get(), 6.825, DELTA);
        foo.values.get(3).setXValue(10d);
        System.out.println(foo.averageBinding().get()); //Should  print 10.075
        Assert.assertEquals(foo.averageBinding().get(), 10.075, DELTA);
    }
}
