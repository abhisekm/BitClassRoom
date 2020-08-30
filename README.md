# BitClassRoom
Kotlin, Room and MVVM implementation of a virtual classroom

## Mock Api

Here are few of the assumptions I made on the Mock Api

```json
{
    "id": "1", 
    "createdAt": 1598020398, 
    "code": "code 1",
    "heading": "heading 1",
    "start_time": 1598020398, 
    "end_time": 1598020398, 
}
```

**id** - Agora class id (can be used as UID for class) <br>
**createdAt** - class creation time <br>
**code** - Lesson Code eg - Java <br>
**heading** - Lesson Heading eg - Java Multithreading <br>
**start_time** - Lesson start time <br>
**end_time** - Lesson end time <br>
<br>
I am displaying the code, heading and start time for each lesson.

## Agora SDK
I have implemented local audio, video toggle along with switch camera. They are controlled by the bottom 4 buttons <br><br>
I have also implemeted remote video and audio toggle too. These are controlled by the top left two buttons
