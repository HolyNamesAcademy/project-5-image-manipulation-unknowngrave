Image Manipulation
=========

Good work pledge
-----
We are here to broaden your exposure to Computer Science. We can only achieve that purpose when you work hard and honestly. It may be tempting to copy-paste code from a classmate, or let a classmate do all your work for you—don't! You will be cheating yourself from the most valuable thing course has to offer—overcoming challenges.

We know that hard, and honest work doesn't come easily. If you feel like you are falling behind:

1. Don't copy-paste code, or let someone do your work for you
2. Ask for help!
3. Tell the teaching-team you need more time 

Getting started
-----
Go to the [Getting Started With The First Project](https://holynamesseattle.sharepoint.com/sites/Section_6558/_layouts/OneNote.aspx?id=%2Fsites%2FSection_6558%2FSiteAssets%2FProjects%20in%20Comp%20Sci%20-%20Mon-Wed%2019-20%20Notebook&wd=target%28Class%20Overview.one%7C74AD5220-0070-4A9A-BD5E-85B1624E453C%2FGetting%20Started%20With%20A%20Project%7C127DA7EC-BEEC-4463-BE97-A79C378AD455%2F%29) in the OneNote (I suggest you look in the OneNote, since it also has screenshots to follow along with, but the instructions are also below) and follow the instructions to open the project in IntelliJ.

1. Go to this link: https://classroom.github.com/a/Ek3YlSwW and accept the assignment. It should take you to your project page. (If it doesn't, click on the link above again, and click on the link to the project page).
2. On the GitHub project page, click on the green "Clone or Download" button and copy the link.
3. Open Intellij, click on the "Checkout from Version Control" drop down and select "GitHub"
4. On the next page paste the link you copied into the "Git Repository URL" box. 
5. Click Clone. You may have to enter your GitHub username and password.
6. If it asks you whether you want to open the project or not, select yes.
7. You should see your project open. If you need to reopen the project, you should see it under "File > Open Recent"

Background
-----
There are various ways to represent visual images in digital format. One easy way to represent images is via a 2-D array where each grid represents a color:

![2-D array example](http://patriotcomputerlab.weebly.com/uploads/2/5/0/6/25060290/screen-shot-2017-02-08-at-8-48-36-am_1.png)

The color space in each element of the array can be represented as either RGB or HSL.

### RGB

[RGB](https://en.wikipedia.org/wiki/RGB_color_model) is a way of representing colors by constructing them from various intensities of red, green, and blue light. They are most often represented in programming as a tuple:

    ( red: 145, green: 198, blue: 54 )

The intensity of each component color is a value between 0 and 255.

### HSL

[HSL](https://en.wikipedia.org/wiki/HSL_and_HSV) is an alternate way of representing a color space by describing it's hue, saturation, and lightness, similar to how a color wheel represents colors. Like RGB, it can also be represented as a tuple:

    ( hue: 285, sat: 0.36, lightness: 0.87 )
    
Unlike RGB, each component is represented differently:

- Hue: a value between 0 and 360
- Saturation: a value between 0 and 1
- Lightness: a value between 0 and 1

The Wikipedia links above contain much more information about each of these color spaces but for this assignment, these basics should suffice.

The Project
-----
In this project, you will be implementing functionality to manipulate images in various ways.

The program allows you to run several commands that allows you to manipulate images; see below for more detailed information about each of these image manipulations. Here are the commands that the program allows you to run:
- **load** and **save**: When you run the program, you will need to call **load** with the file you wish the manipulate and **save** after you manipulate the image.
- **quit**: has also been implemented for you.
- **grayscale**: will take the loaded image and convert it to grayscale.
- **invert**: will invert each pixel of the image.
- **sepia**: will take the loaded image and convert it to sepia.
- **bw**: will take the loaded image and convert it to black and white.
- **rotate**: will rotate the image 90 degrees clockwise.
- **instagram**: will apply a halo and grain effect ala Instagram.
- **hue**, **saturation**, and **lightness** will adjust the hue, saturation, and lightness of the image.

These are the functions you will implement:

- In RGB.java:
    - **RGB**: There are two constructors that need to be implemented. Details are in the comments above the methods and should be self explanatory.
    - **(Get|Set)(Red|Green|Blue)**: Gettors and settors for the member variables need to be implemented.

- In HSL.java:
    - **HSL**: Just one constructor to implement.
    - **(Get|Set)(Hue|Saturation|Lightness)**: Gettors and settors for the member variables need to be implemented.

- In ImageManipulator.java (the comments above each method explain what needs to be done and the unit tests can be used to verify):
    - **Load** (hint: the Img class provides mechanisms to load and save, use those rather than trying to come up with special logic)
    - **Save**
    - **ConvertToGrayScale**
    - **InvertImage**
    - **ConvertToSepia**
    - **ConvertToBW**
    - **RotateImage**
    - **InstagramFilter**
    - **SetHue**
    - **SetSaturation**
    - **SetLightness**
    
Start off by implementing all the RGB methods as nothing else will work until this is done. Then we suggest starting to implenting the methods in ImageManipulator; the first three are the easiest, the next two are a bit more difficult, and the last four rather tough. Note that you will want to implement all the methods in the HSL class before proceeding to the last three methods in ImageManipulator.
    
Committing your Changes and Turning In The Project
-----
The same instructions with screenshots are in the OneNote at the bottom of the page [here](https://holynamesseattle.sharepoint.com/sites/Section_6558/_layouts/OneNote.aspx?id=%2Fsites%2FSection_6558%2FSiteAssets%2FProjects%20in%20Comp%20Sci%20-%20Mon-Wed%2019-20%20Notebook&wd=target%28Class%20Overview.one%7C74AD5220-0070-4A9A-BD5E-85B1624E453C%2FGetting%20Started%20With%20A%20Project%7C127DA7EC-BEEC-4463-BE97-A79C378AD455%2F%29).

At the end of every class period, you should commit your changes. "Committing your changes" is basically a fancy way of saving the changes you made. It is very important and useful for two reasons:

1. You save your changes online, so you can never lose them. Even if your computer breaks, your changes will still be saved somewhere.
2. You can go back to any previous version that you committed. So if you accidentally make a wrong change that breaks your program, you can always go back to a state where the program was working.

You can commit and push ("push" means send it to GitHub.com to save it there) by doing the steps below:

1. Once you are ready to save your changes, click on VCS > Commit Changes… in the taskbar. 
2. It will show you a list of files that you have made changes to. (It might also show changes to a file called workspace.xml, which you didn’t touch. That's ok, IntelliJ modifies that file behind the scenes). You can double click on any file and see the changes you've made to the file. 
3. Hover over the commit button and select "Commit and Push". It will prompt you, asking if you are sure. Select "Commit", and then on the next page, select "Push".
4. Go to your project page on GitHub, and make sure that your changes are there. 
5. That's it. The last change you submit before the deadline will be considered your turned in assignment. You can turn in additional submissions after the deadline, but remember that there is a 10% penalty added each week after the deadline you turn in the assignment.

# Grading

Your grade for each project will fall into one of four categories:

| Grade Level         | Explanation |
| :------------------ |:----------- |
| *Exceeds Expectations*        | <ul><li>Quality is outstanding.</li></ul> |
| *Excellent*                   | <ul><li>Overall quality is high.</li></ul> |
| *Satisfactory*                | <ul><li>Overall quality is good.</li><li>Improvements can be made to bring the quality up to <i>Excellent</i>.</li></ul> |
| *Needs Improvement*           | <ul><li>Overall quality is not yet high enough and the submission will not be accepted.</li><li>Improvements must be made to bring the quality up to at least <i>Satisfactory</i>.</li></ul> |
