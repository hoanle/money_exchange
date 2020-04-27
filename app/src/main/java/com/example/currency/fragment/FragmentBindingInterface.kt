package com.example.currency.fragment

/**
 * Interface for loading and data binding fragment
 * T: Binding class
 */
interface FragmentBindingInterface<T> {
    fun performBinding(binding: T)
}
