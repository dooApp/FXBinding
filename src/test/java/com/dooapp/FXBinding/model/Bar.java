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

import com.dooapp.FXBinding.FXStringBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created at 21/06/13 17:58.<br>
 *
 * @author <a href="mailto:christophe@dooapp.com">Christophe DUFOUR</a>
 */
public class Bar {
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
        private final StringProperty nameProperty = new SimpleStringProperty();
        public StringProperty stringProperty() {
            return nameProperty;
        }
    }
    private final ObjectProperty<A> aProperty = new SimpleObjectProperty<A>();
    private StringBinding nameBinding = new FXStringBinding() {
        @Override
        protected void configure() {
            try {
                addObservable(aProperty);
                addObservable(aProperty.get().bProperty());
                addObservable(aProperty.get().bProperty().get().cProperty());
                addObservable(aProperty.get().bProperty().get().cProperty().get().stringProperty());
            } catch (NullPointerException ignored) {
            }
        }
        @Override
        protected String compute() {
            try {
                return aProperty.get().bProperty().get().cProperty().get().stringProperty().get();
            } catch (NullPointerException e) {
                return null;
            }
        }
    };
}
