# TimeTracker

## Features

1. **Task Creation:** Users can easily create new tasks by providing a title, description, and duration.
2. **Task Progress:** Each task's progress is tracked and stored using the Room database, allowing users to resume their tasks from where they left off.
3. **Task Playback:** Users can play a task to start tracking their progress. The app provides controls to pause, resume, or stop the task playback.
4. **Swipe-to-Delete:** To delete a task, users can simply swipe it left or right, providing a convenient way to remove unwanted or completed tasks.
5. **Material UI:** The app is designed using Material UI, providing a visually appealing and intuitive user interface.
6. **Layouts:** Various layouts, such as RecyclerView, have been used to display tasks and provide a smooth scrolling experience.
7. **CRUD Operations:** TimeTracker implements Create, Read, Update, and Delete (CRUD) operations to enable seamless task management.

## Technologies and Concepts

TimeTracker incorporates the following topics and concepts:

1. **RecyclerView:** Utilized to display tasks in a list format, offering efficient scrolling and improved performance.
2. **Room Database:** Used as a local persistence solution to store task information and track task progress reliably.
3. **Material UI:** The app's user interface is designed following Material Design guidelines, ensuring a modern and visually appealing look.
4. **Layouts:** Various layouts, including ConstraintLayout, LinearLayout, and FrameLayout, have been used to structure the app's screens and provide optimal user experience.
5. **CRUD Operations:** The app enables users to perform Create, Read, Update, and Delete operations on tasks, allowing seamless management and organization.

## Benefits

TimeTracker offers several benefits to its users, including:

1. **Enhanced Productivity:** By efficiently managing tasks and tracking progress, TimeTracker helps users stay focused and productive.
2. **Task Organization:** Users can create, edit, and delete tasks easily, enabling effective organization and prioritization of activities.
3. **Progress Tracking:** The ability to resume tasks from the point of interruption allows users to accurately track their progress over time.
4. **User-Friendly Interface:** With a visually appealing Material UI design and intuitive controls, TimeTracker provides a seamless user experience.

## Database Implementation

TimeTracker utilizes Room database, an abstraction layer over SQLite, to manage task data. Room simplifies data persistence by handling the creation and management of the database, as well as providing convenient access to the data through annotated Java/Kotlin objects.

To create the Room database in TimeTracker, the following steps were followed:

1. Define the Entity: A data model class, representing a task, was created and annotated with `@Entity`. The fields of the entity class correspond to the columns in the database table.
2. Define the Data Access Object (DAO): A DAO interface was created, annotated with `@Dao`, to define the database operations (such as inserting, updating, deleting, and querying tasks).
3. Create the Database: A Room database class was implemented, extending `RoomDatabase`. This class serves as the main access point to the database and provides methods to obtain the DAO interface.
4. Annotate the Database: The Room database class was annotated with `@Database`, specifying the entities and the database version.
5. Implement Repository: A repository class was created to abstract the data access operations and provide a clean API for the app's data layer.

By utilizing Room database, TimeTracker ensures efficient and reliable storage of task information and progress.

## Conclusion

TimeTracker is a powerful task management app that helps users stay organized and productive. By incorporating RecyclerView, Room database, Material UI, layouts, and CRUD operations, the app provides a user-friendly experience while enabling efficient task creation, tracking, and management. Whether it's managing personal to-do lists or tracking project milestones, TimeTracker is a valuable tool for enhancing productivity and achieving goals.
