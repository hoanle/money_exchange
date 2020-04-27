# Money conversion

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

The project is to allow user to do money exchange 
  - User can select a currency from a popup
  - User can fill in the amount he wants to do the exchange
  - All currencies and rates are from https://currencylayer.com/quickstart 
  - User can see all values after being exchanged, scroll down to see more
# Tech details
  - Architecture: **MVVM** for demonstrating purpose. MVP can be used because it is less complex.  
  - **Databinding** is used in some Fragments
  - DI: **Koin**, most of the ViewModels, Fragments, FragmentFactories, Repositoiries are code in a way that they can be injected and obtained by Koin at anytime
  - Network call: **Retrofit** with OkHttp3 and a custom adapter. **Coroutines** with suspend function can be used instead
  - **Rx** is also used for observing parameter, replacing callbacks & retrofit
  - **LiveData** is used to communicate between ViewModel & UI
# Testing
  - **Junit4**(4.12) is used. 
  - **Espresso** is used to test some UI coponent. 
  - **mockk** is used for mocking object, functions...
  - Testing forcus on ViewModels, Repositories, Fragments and Activities

### Installation

The project should be installed directly on emulator or Android devices without issues

### Todos
 - Improve UIs
 - Add Night Mode
 - Optimise code
 - More test cases
License
----

MIT
