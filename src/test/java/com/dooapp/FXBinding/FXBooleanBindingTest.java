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
package com.dooapp.FXBinding;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created at 17/06/13 11:56.<br>
 *
 * @author <a href="mailto:christophe@dooapp.com">Christophe DUFOUR</a>
 */
public class FXBooleanBindingTest {
    //Class definition
    public class A {
        private final ObjectProperty<B> bProperty = new SimpleObjectProperty<B>();
        public ObjectProperty<B> bProperty() {
            return bProperty;
        }
    }
    public class B {
        private final ObjectProperty<C> cProperty = new SimpleObjectProperty<C>();
        public ObjectProperty<C> cProperty() {
            return cProperty;
        }
    }
    public class C {
        private final BooleanProperty okProperty = new SimpleBooleanProperty();
        public BooleanProperty okProperty() {
            return okProperty;
        }
    }
    private final ObjectProperty<A> aProperty = new SimpleObjectProperty<A>();
    @Before
    public void clearA() {
        aProperty.set(null);
    }
    private BooleanBinding tested = new FXBooleanBinding() {
        @Override
        protected void configure() {
            try {
                addObservable(aProperty);
                addObservable(aProperty.get().bProperty());
                addObservable(aProperty.get().bProperty().get().cProperty());
                addObservable(aProperty.get().bProperty().get().cProperty().get().okProperty());
            } catch (NullPointerException e) {
                //Nothing to say
            }
        }
        @Override
        protected boolean compute() {
            try {
                return aProperty.get().bProperty().get().cProperty().get().okProperty().get();
            } catch (NullPointerException e) {
                return false;
            }
        }
    };
    @Test
    public void testIsValid() {
        A a1 = new A();
        A a2 = new A();
        A a3 = new A();
        B b1 = new B();
        a1.bProperty().set(b1);
        B b2 = new B();
        a2.bProperty().set(b2);
        B b3 = new B();
        a3.bProperty().set(b3);
        aProperty.set(a1);
        aProperty.set(a2);
        aProperty.set(a3);
        assertThat(tested.get(), is(false));
        a1.bProperty().set(new B());
        assertThat(tested.isValid(), is(true));
        b1.cProperty().set(new C());
    }
    @Test
    public void simpleTest() {
        assertThat(tested.get(), is(false));
        A a = new A();
        B b = new B();
        C c = new C();
        c.okProperty().set(true);
        assertThat(tested.get(), is(false));
        aProperty.set(a);
        a.bProperty().set(b);
        b.cProperty().set(c);
        assertThat(tested.get(), is(true));
        c.okProperty().set(false);
        b.cProperty().set(null);
        assertThat(tested.get(), is(false));
        c.okProperty().set(false);
        assertThat(tested.isValid(), is(true));
    }
}
