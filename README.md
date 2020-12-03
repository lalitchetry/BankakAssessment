# BankakAssessment

This project is configured with MVVM Architecture, Dagger, Retrofit, DataBinding.

In this project, first the API is hit and the view has been generated dynamically from the response of the API.

A dynamic class (DynamicView.java) is created to generate the Spinner, TextView and EditText.

A common class has been created for all snackbar diaplay, checking of internet connectivity, creating of custom loader and validation as per the API (regex).

In the repository class (TypeRepo.java), API has been called using retrofit call method. In viewmodel class (TypeViewModel.java), the logic of storing data in applied. In the MainActivity.java, the data has been displayed as per the options.
