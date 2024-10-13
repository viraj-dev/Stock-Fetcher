# Stock Fetcher

Stock Fetcher is an Android application that allows users to search for stock information of companies listed in the Indian stock market. It fetches the current stock price, percentage change, company details, and industry information.

## Table of Contents
1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Setup](#setup)
4. [Running the App](#running-the-app)
5. [Usage](#usage)
6. [Built With](#built-with)
7. [Contributing](#contributing)
8. [License](#license)

## Features
- Search for a stock by its symbol.
- View current stock price, percentage change, company name, and industry.
- Displays a loading state while fetching data.
- Error handling for invalid symbols, network issues, and rate limits.

## Prerequisites
Before setting up the project, make sure you have the following:
- [Android Studio](https://developer.android.com/studio) installed.
- Android device or emulator to run the app.
- API key from [RapidAPI](https://rapidapi.com/) for the Indian Stock Exchange API.

## Setup
Follow these steps to set up and run the app:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/StockFetcher.git
   cd StockFetcher

 ## Running the App

1. **Set Up an Emulator or Physical Device**
   - Ensure your emulator is running, or connect an Android device with USB Debugging enabled.

2. **Run the App**
   - Click the "Run" button in Android Studio, or press `Shift + F10`.
   - Choose your device or emulator, and the app should launch automatically.
  ## Usage

1. **Search for a Stock**
   - Enter the stock symbol in the search bar (e.g., "RELIANCE").
   - Press the "Search Stock" button to fetch the stock price and details.

2. **View Stock Information**
   - The app displays the current stock price, percentage change, company name, and industry information for the entered stock symbol.
   - The stock price text color indicates the price change (green for an increase and red for a decrease).

3. **Automatic Retry for API Rate Limits**
   - If the app hits the API rate limit, it automatically retries after a short delay.

## Built With

- **Java**: The primary programming language used for app development.
- **Android Studio**: The official integrated development environment (IDE) for Android app development.
- **Volley**: A networking library used for making API requests.
- **Material Design Components**: Used for the app's UI components to follow modern design guidelines.

## Contributing

We welcome contributions to improve the Stock Fetcher app! If you would like to contribute, please follow these steps:

1. **Fork the Repository**  
   - Click the "Fork" button at the top-right corner of this repository to create your own copy.

2. **Clone the Forked Repository**  
   - Clone the forked repository to your local machine using the command:
     ```bash
     git clone https://github.com/your-username/stock-fetcher.git
     ```
   - Replace `your-username` with your GitHub username.

3. **Create a New Branch**  
   - Create a new branch for your feature or bug fix:
     ```bash
     git checkout -b feature-name
     ```
   - Replace `feature-name` with a relevant name for your branch.

4. **Make Your Changes**  
   - Make the necessary changes to the codebase.
   - Ensure that your code follows the project's coding guidelines.

5. **Commit Your Changes**  
   - Add and commit your changes with a descriptive commit message:
     ```bash
     git add .
     git commit -m "Description of the changes made"
     ```

6. **Push Your Changes**  
   - Push your changes to your forked repository:
     ```bash
     git push origin feature-name
     ```

7. **Create a Pull Request**  
   - Go to the original repository and click the "New Pull Request" button.
   - Select the branch you just pushed and submit a pull request with a description of your changes.

8. **Review and Feedback**  
   - Your pull request will be reviewed, and feedback may be provided. Please make any necessary changes requested by the reviewers.

9. **Merge the Pull Request**  
   - Once your pull request is approved, it will be merged into the main branch.

Thank you for your contributions!
