# lucene-json-example
An example of indexing and searching JSON documents using Lucene.

## Building the Example
Run the following command to build the example:

    ./gradlew clean build
    
## Running the Example
Run the following command to execute the example application:

    ./gradlew run
    
If successful, you will see that a new index is created and several queries are run against it:

    > Task :run
    [main] INFO example.lucene.Application - Creating a new search index for json files: /Users/greg/workspace/lucene-example/documents
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Opening search index: /Users/greg/workspace/lucene-example/idx
    [main] INFO example.lucene.Application - Adding json files to search index...
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Adding Product '001' to search index
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Adding Product '002' to search index
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Adding Product '003' to search index
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Adding Product '004' to search index
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Adding Product '005' to search index
    [main] INFO example.lucene.indexing.ProductJsonIndexWriter - Closing search index: /Users/greg/workspace/lucene-example/idx
    [main] INFO example.lucene.Application - ==============================================
    [main] INFO example.lucene.Application - Query: findAllMensProducts
    [main] INFO example.lucene.Application - Found: Shoe 001
    [main] INFO example.lucene.Application - Found: Shoe 003
    [main] INFO example.lucene.Application - ==============================================
    [main] INFO example.lucene.Application - Query: findAllWomensProducts
    [main] INFO example.lucene.Application - Found: Shoe 002
    [main] INFO example.lucene.Application - Found: Blue Womens Tee
    [main] INFO example.lucene.Application - ==============================================
    [main] INFO example.lucene.Application - Query: findAllWomensApparel
    [main] INFO example.lucene.Application - Found: Blue Womens Tee
    [main] INFO example.lucene.Application - ==============================================
    [main] INFO example.lucene.Application - Query: findAllProductsBetweenFortyFiveAndOneHundredDollars
    [main] INFO example.lucene.Application - Found: Shoe 003
    [main] INFO example.lucene.Application - Found: Blue Womens Tee

## Bugs and Feedback
For bugs, questions, comments, and discussions please use the [Github Issues](https://github.com/gregwhitaker/lucene-json-example/issues).

## License
MIT License

Copyright (c) 2020 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.