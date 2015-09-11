/*
 * Copyright 2010, 2013, 2015 Jan Ouwens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.jqno.equalsverifier;

import nl.jqno.equalsverifier.internal.ClassAccessor;
import nl.jqno.equalsverifier.internal.FieldAccessor;
import nl.jqno.equalsverifier.internal.FieldIterable;
import nl.jqno.equalsverifier.internal.ObjectAccessor;

import java.lang.reflect.Field;

class FieldInspector<T> {
    private final ClassAccessor<T> classAccessor;

    public FieldInspector(ClassAccessor<T> classAccessor) {
        this.classAccessor = classAccessor;
    }

    public void check(FieldCheck check) {
        for (Field field : FieldIterable.of(classAccessor.getType())) {
            ObjectAccessor<T> reference = classAccessor.getRedAccessor();
            ObjectAccessor<T> changed = classAccessor.getRedAccessor();

            check.execute(reference.fieldAccessorFor(field), changed.fieldAccessorFor(field));
        }
    }

    public void checkWithNull(FieldCheck check) {
        for (Field field : FieldIterable.of(classAccessor.getType())) {
            ObjectAccessor<T> reference = classAccessor.getDefaultValuesAccessor();
            ObjectAccessor<T> changed = classAccessor.getDefaultValuesAccessor();

            check.execute(reference.fieldAccessorFor(field), changed.fieldAccessorFor(field));
        }
    }

    public interface FieldCheck {
        void execute(FieldAccessor referenceAccessor, FieldAccessor changedAccessor);
    }
}
