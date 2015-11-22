# Rulinker

This program can help you create Russian flashcards for use with Anki. It will create links to Google Images, Forvo, Lingvo and both English and Russian Wiktionary for every Russian word on the card. Of course, the Wiktionary pages contain conjugation tables, so you don't need to include them. See the screenshot below.

![Screenshot](http://i.imgur.com/EJ9iG6Q.png)

## Instructions

1. This is a Java (actually Clojure) program, and you need to have Java installed. Also, you need to have it in your path, i.e. be able to run it from the command line. If you don't have Java installed, you can download it from [Oracle's website](http://www.oracle.com/technetwork/java/javase/downloads/index.html). Pick the JRE (Java Runtime Environment).
1. Download the Zip of this repository (to the right) and unzip it.
1. Create a directory structure that looks like this:

 ```
your-russian-directory
├───rulinker (unzipped zip)
├───input
└───output
```

 The program will take all of the text files in the `input` directory, and create new text files in the `output` directory. Your input text files will not be modified.
1. Your text files should have the following format.

 ```
russian word or phrase; english translation (or whatever really)
```

 For example:

 ```
я иду домой; I am going home
делать (i); to do
```

 Note that you *can* have non-Russian words on the left-hand side, but if you have Russian words on the right-hand side, they will not be processed.
1. Run (double-click) `rulinker-standalone.bat`. That should run the program and create output files in the `output` directory. If you are on a non-Windows OS, just run the single command inside the bat file.
1. Import the new text files created in the `output` directory into Anki.
1. Note the following:
  1. The output files will reverse the Russian and the translation, i.e. they will look like this: `translation; russian`, because I think that is standard in Anki. However, I wanted to write my files with Russian first.
  1. The lines will be shuffled, because when trying to remember closely related words at the same, you can mix them up in your head, but you may want them together in your source text file.
  1. You can write `(i)` and `(p)` (for imperfective and perfective) on the left-hand side, and they will be copied over to the translation in the output, so that you know when practising what the card is asking for. Example: 

     ```
    делать (i) сделать (p); to do
    ```

     You can also write it like this:

     ```
    делать // сделать; to dо
    ```

     The two slashes will also result in `(i) (p)` on the translation side.

