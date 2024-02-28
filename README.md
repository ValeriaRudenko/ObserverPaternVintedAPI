# VintedObserverAPI

## Overview

VintedObserverAPI is a project that extends the functionality of a previous practice, Practise 7, which implemented a program using the Observer Pattern. In this extension, the Model-View-Controller (MVC) Pattern is introduced to provide a scoreboard containing various charts representing data extracted from a JSON file located in a remote repository.

## Features

- **Search Functionality and Filters:**

  - Comprehensive search feature with a range of filters and criteria.
  - Specify minimum and maximum number of favorites and items for each brand.
  - Choose charts to be included in the scoreboard.

- **Results Presentation:**

  - Dynamically showcases search results in a dedicated frame.
  - Organized and visually appealing display of selected data through charts.

- **Intuitive User Interface:**
  - User-friendly graphical user interface (GUI).
  - Accommodates users with diverse technical expertise.

## Technologies Used

- **Observer Pattern:**

  - Seamless integration for effective communication among components.
  - Ensures prompt updates across the system.

- **Factory Method:**

  - Used for the creation of different charts included in the scoreboard.
  - Enables dynamic and on-demand creation based on user input.

- **Strategy Pattern:**
  - Implemented for parsing different JSON files (Vinted API and remote repository).
  - Promotes a modular and maintainable design for parsing strategies.
