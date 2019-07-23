package com.shuyu.github.kotlin.common.gsyimageloader

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.Proxy
import java.util.Collections
import java.util.HashMap

/**
 * Collection of helper methods for calling methods and accessing fields reflectively.
 */
object GSYReflectionHelpers {
    private val PRIMITIVE_RETURN_VALUES = Collections.unmodifiableMap(object : HashMap<String, Any>() {
        init {
            put("boolean", java.lang.Boolean.FALSE)
            put("int", 0)
            put("long", 0.toLong())
            put("float", 0.toFloat())
            put("double", 0.toDouble())
            put("short", 0.toShort())
            put("byte", 0.toByte())
        }
    })!!

    fun <T> createNullProxy(clazz: Class<T>): T {
        return Proxy.newProxyInstance(clazz.classLoader,
                arrayOf<Class<*>>(clazz)) { proxy, method, args -> PRIMITIVE_RETURN_VALUES[method.returnType.name] } as T
    }

    fun <A : Annotation> defaultsFor(annotation: Class<A>): A {
        return annotation.cast(
                Proxy.newProxyInstance(annotation.classLoader, arrayOf<Class<*>>(annotation)
                ) { proxy, method, args -> method.defaultValue })
    }

    /**
     * Reflectively get the value of a field.
     *
     * @param object    Target object.
     * @param fieldName The field name.
     * @param <R>       The return type.
     * @return Value of the field on the object.
    </R> */
    fun <R> getField(`object`: Any, fieldName: String): R {
        try {
            return traverseClassHierarchy(`object`.javaClass, NoSuchFieldException::class.java, object : InsideTraversal<R> {
                @Throws(Exception::class)
                override fun run(traversalClass: Class<*>): R {
                    val field = traversalClass.getDeclaredField(fieldName)
                    field.isAccessible = true
                    return field.get(`object`) as R
                }
            })
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively set the value of a field.
     *
     * @param object        Target object.
     * @param fieldName     The field name.
     * @param fieldNewValue New value.
     */
    fun setField(`object`: Any, fieldName: String, fieldNewValue: Any) {
        try {
            traverseClassHierarchy(`object`.javaClass, NoSuchFieldException::class.java, object : InsideTraversal<Any?> {
                @Throws(Exception::class)
                override fun run(traversalClass: Class<*>): Any? {
                    val field = traversalClass.getDeclaredField(fieldName)
                    field.isAccessible = true
                    field.set(`object`, fieldNewValue)
                    return null
                }
            })
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively set the value of a field.
     *
     * @param type          Target type.
     * @param object        Target object.
     * @param fieldName     The field name.
     * @param fieldNewValue New value.
     */
    fun setField(type: Class<*>, `object`: Any, fieldName: String, fieldNewValue: Any) {
        try {
            val field = type.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(`object`, fieldNewValue)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively get the value of a static field.
     *
     * @param field Field object.
     * @param <R>   The return type.
     * @return Value of the field.
    </R> */
    fun <R> getStaticField(field: Field): R {
        try {
            makeFieldVeryAccessible(field)
            return field.get(null) as R
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively get the value of a static field.
     *
     * @param clazz     Target class.
     * @param fieldName The field name.
     * @param <R>       The return type.
     * @return Value of the field.
    </R> */
    fun <R> getStaticField(clazz: Class<*>, fieldName: String): R {
        try {
            return getStaticField(clazz.getDeclaredField(fieldName))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively set the value of a static field.
     *
     * @param field         Field object.
     * @param fieldNewValue The new value.
     */
    fun setStaticField(field: Field, fieldNewValue: Any) {
        try {
            makeFieldVeryAccessible(field)
            field.set(null, fieldNewValue)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively set the value of a static field.
     *
     * @param clazz         Target class.
     * @param fieldName     The field name.
     * @param fieldNewValue The new value.
     */
    fun setStaticField(clazz: Class<*>, fieldName: String, fieldNewValue: Any) {
        try {
            setStaticField(clazz.getDeclaredField(fieldName), fieldNewValue)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively call an instance method on an object.
     *
     * @param instance        Target object.
     * @param methodName      The method name to call.
     * @param classParameters Array of parameter types and values.
     * @param <R>             The return type.
     * @return The return value of the method.
    </R> */
    fun <R> callInstanceMethod(instance: Any, methodName: String, vararg classParameters: ClassParameter<*>): R {
        try {
            val classes = ClassParameter.getClasses(*classParameters)
            val values = ClassParameter.getValues(*classParameters)

            return traverseClassHierarchy(instance.javaClass, NoSuchMethodException::class.java, object : InsideTraversal<R> {
                @Throws(Exception::class)
                override fun run(traversalClass: Class<*>): R {
                    val declaredMethod = traversalClass.getDeclaredMethod(methodName, *classes)
                    declaredMethod.isAccessible = true
                    return declaredMethod.invoke(instance, *values) as R
                }
            })
        } catch (e: InvocationTargetException) {
            if (e.targetException is RuntimeException) {
                throw e.targetException as RuntimeException
            }
            if (e.targetException is Error) {
                throw e.targetException as Error
            }
            throw RuntimeException(e.targetException)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively call an instance method on an object on a specific class.
     *
     * @param cl              The class.
     * @param instance        Target object.
     * @param methodName      The method name to call.
     * @param classParameters Array of parameter types and values.
     * @param <R>             The return type.
     * @return The return value of the method.
    </R> */
    fun <R> callInstanceMethod(cl: Class<*>, instance: Any, methodName: String, vararg classParameters: ClassParameter<*>): R {
        try {
            val classes = ClassParameter.getClasses(*classParameters)
            val values = ClassParameter.getValues(*classParameters)

            val declaredMethod = cl.getDeclaredMethod(methodName, *classes)
            declaredMethod.isAccessible = true
            return declaredMethod.invoke(instance, *values) as R
        } catch (e: InvocationTargetException) {
            if (e.targetException is RuntimeException) {
                throw e.targetException as RuntimeException
            }
            if (e.targetException is Error) {
                throw e.targetException as Error
            }
            throw RuntimeException(e.targetException)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively call a static method on a class.
     *
     * @param clazz           Target class.
     * @param methodName      The method name to call.
     * @param classParameters Array of parameter types and values.
     * @param <R>             The return type.
     * @return The return value of the method.
    </R> */
    fun <R> callStaticMethod(clazz: Class<*>, methodName: String, vararg classParameters: ClassParameter<*>): R {
        try {
            val classes = ClassParameter.getClasses(*classParameters)
            val values = ClassParameter.getValues(*classParameters)

            val method = clazz.getDeclaredMethod(methodName, *classes)
            method.isAccessible = true
            return method.invoke(null, *values) as R
        } catch (e: InvocationTargetException) {
            if (e.targetException is RuntimeException) {
                throw e.targetException as RuntimeException
            }
            if (e.targetException is Error) {
                throw e.targetException as Error
            }
            throw RuntimeException(e.targetException)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Load a class.
     *
     * @param classLoader             The class loader.
     * @param fullyQualifiedClassName The fully qualified class name.
     * @return The class object.
     */
    fun loadClass(classLoader: ClassLoader, fullyQualifiedClassName: String): Class<*> {
        try {
            return classLoader.loadClass(fullyQualifiedClassName)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }

    }

    /**
     * Create a new instance of a class
     *
     * @param cl  The class object.
     * @param <T> The class type.
     * @return New class instance.
    </T> */
    fun <T> newInstance(cl: Class<T>): T {
        try {
            return cl.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reflectively call the constructor of an object.
     *
     * @param clazz           Target class.
     * @param classParameters Array of parameter types and values.
     * @param <R>             The return type.
     * @return The return value of the method.
    </R> */
    fun <R> callConstructor(clazz: Class<out R>, vararg classParameters: ClassParameter<*>): R {
        try {
            val classes = ClassParameter.getClasses(*classParameters)
            val values = ClassParameter.getValues(*classParameters)

            val constructor = clazz.getDeclaredConstructor(*classes)
            constructor.isAccessible = true
            return constructor.newInstance(*values)
        } catch (e: InstantiationException) {
            throw RuntimeException("error instantiating " + clazz.name, e)
        } catch (e: InvocationTargetException) {
            if (e.targetException is RuntimeException) {
                throw e.targetException as RuntimeException
            }
            if (e.targetException is Error) {
                throw e.targetException as Error
            }
            throw RuntimeException(e.targetException)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @Throws(Exception::class)
    private fun <R, E : Exception> traverseClassHierarchy(targetClass: Class<*>, exceptionClass: Class<out E>, insideTraversal: InsideTraversal<R>): R {
        var hierarchyTraversalClass: Class<*>? = targetClass
        while (true) {
            try {
                return insideTraversal.run(hierarchyTraversalClass!!)
            } catch (e: Exception) {
                if (!exceptionClass.isInstance(e)) {
                    throw e
                }
                hierarchyTraversalClass = hierarchyTraversalClass!!.superclass
                if (hierarchyTraversalClass == null) {
                    throw RuntimeException(e)
                }
            }

        }
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    private fun makeFieldVeryAccessible(field: Field) {
        field.isAccessible = true

        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
    }

    fun defaultValueForType(returnType: String): Any? {
        return PRIMITIVE_RETURN_VALUES[returnType]
    }

    private interface InsideTraversal<R> {
        @Throws(Exception::class)
        fun run(traversalClass: Class<*>): R
    }

    /**
     * Typed parameter used with reflective method calls.
     *
     * @param <V> The value of the method parameter.
    </V> */
    class ClassParameter<V>(val clazz: Class<out V>, val `val`: V) {
        companion object {

            fun <V> from(clazz: Class<out V>, `val`: V): ClassParameter<V> {
                return ClassParameter(clazz, `val`)
            }

            fun fromComponentLists(classes: Array<Class<*>>, values: Array<Any>): Array<ClassParameter<*>?> {
                val classParameters = arrayOfNulls<ClassParameter<*>>(classes.size)
                for (i in classes.indices) {
                    classParameters[i] = ClassParameter.from(classes[i], values[i])
                }
                return classParameters
            }

            fun getClasses(vararg classParameters: ClassParameter<*>): Array<Class<*>?> {
                val classes = arrayOfNulls<Class<*>>(classParameters.size)
                for (i in classParameters.indices) {
                    val paramClass = classParameters[i].clazz
                    classes[i] = paramClass
                }
                return classes
            }

            fun getValues(vararg classParameters: ClassParameter<*>): Array<Any?> {
                val values = arrayOfNulls<Any>(classParameters.size)
                for (i in classParameters.indices) {
                    val paramValue = classParameters[i].`val`
                    values[i] = paramValue
                }
                return values
            }
        }
    }

    /**
     * String parameter used with reflective method calls.
     *
     * @param <V> The value of the method parameter.
    </V> */
    class StringParameter<V>(val className: String, val `val`: V) {
        companion object {

            fun <V> from(className: String, `val`: V): StringParameter<V> {
                return StringParameter(className, `val`)
            }
        }
    }
}
