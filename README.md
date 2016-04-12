#Codebase

Codebase is an Open Source library of utility classes that are required by Java applications. 

## Motivation behind the Codebase library
The fundamental idea of the Codebase library is to avoid replicating basic utility functionality over and over again. 

In practice, programmers often need to replicate the code of utility classes and, moreover, have a tendency to regard this code as of lesser importance. An obvious consequence is that these classes don't get as thoroughly tested as they often should, often compromising code quality. Therefore, the underlying idea is to put here functionality that are commonly used in the code to avoid that each project/developer to implement its own version and have it throughly tested. 

The main goals of codebase are: 

* Contributing to increasing the overall code quality by offering a library of high quality utility classes (tested and revised) 
* Simplifying the dependencies of applications (using codebase prevents depending on other libraries) 
* Setting a standard for code quality (the code is available in open source to be analyzed) by helping the users of the library to imitate it.

## Design and development of codebase
* The classes included in the main package are general purpose classes used for, among other, parsing command line arguments, file handling, ciphering, string and binary operations. Utility classes for more specialized functionality are organized into appropriate sub-packages. 

* All the code undergoes strict static checking and most of it is unit tested for maximum reliability.

* All methods are carefully documented

* Compliant with Java 1.6 in order to easily embbedable

## How to contribute
If you have suggestions for enhancements or have identified a bug in Codebase are happy to receive your contributions and give you credit. 

Please make sure you read the [CONTRIBUTING.md](./CONTRIBUTING.md) guide.

## Licensing
The codebase library is licensed under the [LGPL license](http://www.gnu.org/copyleft/lesser.html).

In simple terms this means that the authors of the files and documentation of the documentation contained in the is repository grant you permission to use it on your own projects regardless both for open source and commercial purposes as long as they are credited.

A copy of the license file is included in the root of the project.

## Alternatives to Codebase
Other good open source alternatives to codebase are:

* [Apache Commons](http://commons.apache.org) and 
* [Google Guava](https://code.google.com/p/guava-libraries) 

Although similar in spirit, we decided to keep maintaining codebase because it has a lower footprint and is also simpler to understand.

