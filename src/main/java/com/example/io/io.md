

### Java IO类概述表

已经讨论了数据源、目标媒介、输入、输出和各类不同用途的Java IO类，接下来是一张通过输入、输出、基于字节或者字符、以及其他比如缓冲、解析之类的特定用途划分的大部分Java IO类的表格。

|                  | Byte Based                          | Byte Based                        | Character Based                 | Character Based           |
| :--------------- | :---------------------------------- | :-------------------------------- | ------------------------------- | ------------------------- |
|                  | Input                               | Output                            | Input                           | Output                    |
| Basic            | InputStream                         | OutputStream                      | Reader InputStreamReader        | Writer OutputStreamWriter |
| Arrays           | ByteArrayInputStream                | ByteArrayOutputStream             | CharArrayReader                 | CharArrayWriter           |
| Files            | FileInputStream RandomAccessFile    | FileOutputStream RandomAccessFile | FileReader                      | FileWriter                |
| Pipes            | PipedInputStream                    | PipedOutputStream                 | PipedReader                     | PipedWriter               |
| Buffering        | BufferedInputStream                 | BufferedOutputStream              | BufferedReader                  | BufferedWriter            |
| Filtering        | FilterInputStream                   | FilterOutputStream                | FilterReader                    | FilterWriter              |
| Parsing          | PushbackInputStream StreamTokenizer |                                   | PushbackReader LineNumberReader |                           |
| Strings          |                                     |                                   | StringReader                    | StringWriter              |
| Data             | DataInputStream                     | DataOutputStream                  |                                 |                           |
| Data - Formatted |                                     | PrintStream                       |                                 | PrintWriter               |
| Objects          | ObjectInputStream                   | ObjectOutputStream                |                                 |                           |
| Utilities        | SequenceInputStream                 |                                   |                                 |                           |

### IO管道

Java IO中的管道为运行在同一个JVM中的两个线程提供了通信的能力。所以管道也可以作为数据源以及目标媒介。

你不能利用管道与不同的JVM中的线程通信(不同的进程)。在概念上，Java的管道不同于Unix/Linux系统中的管道。在Unix/Linux中，运行在不同地址空间的两个进程可以通过管道通信。在Java中，通信的双方应该是运行在同一进程中的不同线程。

#### 通过Java IO创建管道

可以通过Java IO中的PipedOutputStream和PipedInputStream创建管道。一个PipedInputStream流应该和一个PipedOutputStream流相关联。一个线程通过PipedOutputStream写入的数据可以被另一个线程通过相关联的PipedInputStream读取出来。

#### Java IO管道示例

这是一个如何将PipedInputStream和PipedOutputStream关联起来的简单例子：

```java
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeExample {

    public static void main(String[] args) throws IOException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2));
		final PipedOutputStream _output = new PipedOutputStream();
		final PipedInputStream _input = new PipedInputStream(_output);
        
		//final PipedOutputStream _output = new PipedOutputStream();
		//final PipedInputStream _input = new PipedInputStream();
		//_output.connect(_input);

		executor.execute(new Runnable() {
            @Override
            public void run() {
                try(PipedOutputStream output = _output) {
                    try {
                        System.out.println("write bytes");
                        output.write("hahahahah".getBytes());
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try(PipedInputStream input = _input) {
                    int read;
                    try {
                        System.out.println("read bytes");
                        while ((read = input.read()) != -1) {
                            System.out.println((char) read);
                        }
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.shutdown();
    }
}
```

注：本例忽略了流的关闭。请在处理流的过程中，务必保证关闭流，或者使用jdk7引入的try-resources代替显示地调用close方法的方式。

你也可以使用两个管道共有的connect()方法使之相关联。PipedInputStream和PipedOutputStream都拥有一个可以互相关联的connect()方法。

#### 管道和线程

请记得，当使用两个相关联的管道流时，务必将它们分配给不同的线程。read()方法和write()方法调用时会导致流阻塞，这意味着如果你尝试在一个线程中同时进行读和写，可能会导致线程死锁。

#### 管道的替代

除了管道之外，一个JVM中不同线程之间还有许多通信的方式。实际上，线程在大多数情况下会传递完整的对象信息而非原始的字节数据。但是，如果你需要在线程之间传递字节数据，Java IO的管道是一个不错的选择。



### IO异常处理

#### Try-with-resources

在Java 7中，您可以使用如下`try-with-resource`构造编写上述示例中的代码： 

```
private static void printFileJava7() throws IOException {

    try(FileInputStream input = new FileInputStream("file.txt")) {

        int data = input.read();
        while(data != -1){
            System.out.print((char) data);
            data = input.read();
        }
    }
}
```

注意方法内的第一行： 

```
try(FileInputStream input = new FileInputStream("file.txt")) {
```

这是`try-with-resources`构造。该`FileInputStream` 变量后的括号内声明的`try`关键字。另外，a `FileInputStream`被实例化并分配给变量。

当`try`块完成时，`FileInputStream`将自动关闭。这是可能的，因为`FileInputStream`实现了Java接口`java.lang.AutoCloseable`。实现此接口的所有类都可以在`try-with-resources`构造中使用。

如果从`try-with-resources`块内部抛出异常，并且在`FileInputStream`关闭时`close()`（调用时），则`try`块内抛出的异常将抛出到外部世界。`FileInputStream`禁止关闭时抛出的异常。这与本文中第一个示例中发生的情况相反，使用旧样式异常处理（关闭`finally`块中的资源）。

#### Using Multiple Resources

您可以在`try-with-resources`块中使用多个资源，并使它们全部自动关闭。这是一个例子： 

```
private static void printFileJava7() throws IOException {

    try(  FileInputStream     input         = new FileInputStream("file.txt");
          BufferedInputStream bufferedInput = new BufferedInputStream(input)
    ) {

        int data = bufferedInput.read();
        while(data != -1){
            System.out.print((char) data);
    data = bufferedInput.read();
        }
    }
}
```

此示例在`try`关键字后面的括号内创建两个资源。一个`FileInputStream`和一个`BufferedInputStream`。执行离开`try`块时，这两个资源都将自动关闭。

资源将按与括号内创建/列出顺序相反的顺序关闭。首先`BufferedInputStream`是关闭，然后是`FileInputStream`。