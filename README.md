# Arabic Poetry Encyclopedia

## Overview

The **Arabic Poetry Encyclopedia** is a Java-based application designed to serve as a comprehensive solution for managing and exploring Arabic poetry. It includes various modules such as Root, Poems, Books, Verses, Assign Root, Import Poem From File, Tokenize Verse, Root Info, and Auto Root Verse GUI. The application enables users to add books, poems, assign roots to verses, import poems from files, and tokenize verses. Additionally, it provides various filters to view information about poems containing specific roots, along with their verses and book names. The application maintains a log file, making it a complete solution for an Arabic poetry encyclopedia.

## Project Structure

```plaintext
Arabic Poetry Encyclopedia
|-- lib/
|   -- external-jar1.jar
|   -- external-jar2.jar
|-- db/
|   -- setup.sql
|-- config/
|   -- application.properties (template)
|   -- log4j2.properties
|-- logs/  (Directory for log files)
|-- installer.sh (or installer.bat for Windows)
|-- README.md
|-- app.jar
```

- **lib/**: Contains external JAR files required by the application.
- **db/**: Includes the setup SQL script for database initialization.
- **config/**: Holds configuration files for the application.
  - **application.properties**: Users can modify this file to configure the database and other system application properties.
  - **log4j2.properties**: Configuration file for log4j2 logging framework.
- **logs/**: The directory where log files will be stored.
- **installer.sh**: The shell script for installing the application (or installer.bat for Windows).
- **README.md**: This documentation file.
- **app.jar**: The executable JAR file for the application.

## Installation Guide

### Prerequisites

1. Java Runtime Environment (JRE)
2. MySQL Database Server

### Installation Steps

1. **Download the Application**

   Clone the project repository:

   ```bash
   git clone https://github.com/SoftwareConstructionAndDev/project-se3001-the-squad.git
   ```

2. **Database Setup**

   - Execute the `setup.sql` script provided in the `db/` directory to initialize the database schema.

3. **Configure Database Connection**

   Open the `application.properties` file in the `config/` directory and modify the database connection properties:

   ```properties
   # Database Configuration
   db.url=jdbc:mysql://localhost:3306/arabicpoetryencyclopedia
   db.user=root
   db.password=
   ```

   Update the `db.url`, `db.user`, and `db.password` with your database connection details.

4. **Run the Installer**

   Execute the installer script:

   ```bash
   sh installer.sh
   ```

   or for Windows:

   ```bash
   installer.bat
   ```

   This script will create the necessary directories, copy files, and generate log files.

5. **Run the Application**

   Double-click on `your-app.jar` or run it from the command line:

   ```bash
   java -jar your-app.jar
   ```

   The application should now be running.

### Usage

- Follow the on-screen instructions and explore the various modules of the Arabic Poetry Encyclopedia.

### Notes

- Make sure the MySQL server is running before initializing the database.
- The provided `application.properties` file is a template; users should modify it based on their specific database configuration.

Enjoy using the Arabic Poetry Encyclopedia!
