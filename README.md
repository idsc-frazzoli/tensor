# ch.ethz.idsc.tensor
Library for tensor computation in Java 8.

Version `0.1.4`

Features:
* multi-dimensional arrays: scalars, vectors, matrices, n-linear forms, Lie algebra ad-tensor, ... 
* scalars are numeric values, as well as exact fractions
* other projects can customize the scalars for instance to attach physical units such as `javax.measure.Unit`

The naming of functions is inspired by `Mathematica`.

## Examples 

Solving systems of linear equations

    Tensor matrix = Tensors.matrixInt(new int[][] { { 4, 3 }, { 8, 2 } });
    System.out.println(Pretty.of(matrix));
    System.out.println(Pretty.of(Inverse.of(matrix)));
    
gives

    [
     [ 4  3 ]
     [ 8  2 ]
    ]
    [
     [ -1/8  3/16 ]
     [  1/2  -1/4 ]
    ]

Linear programming

    Tensor x = LinearProgramming.maxLessEquals( //
        Tensors.fromString("[1, 1]"), // cost
        Tensors.fromString("[[4, -1], [2, 1], [-5, 2]]"), // matrix
        Tensors.fromString("[8, 7, 2]")); // rhs
    System.out.println(x);

gives

    [4/3, 13/3]

## Include in your project

Modify the `pom` file of your project to specify `repository` and `dependency` of the tensor library:

    <repositories>
      <repository>
        <id>tensor-mvn-repo</id>
        <url>https://raw.github.com/idsc-frazzoli/tensor/mvn-repo/</url>
        <snapshots>
          <enabled>true</enabled>
          <updatePolicy>always</updatePolicy>
        </snapshots>
      </repository>
    </repositories>
    
    <dependencies>
      <dependency>
        <groupId>ch.ethz.idsc</groupId>
        <artifactId>tensor</artifactId>
        <version>0.1.4</version>
      </dependency>
    </dependencies>

The source code is attached to the `jar` file for your convenience.

    
## Optional

Clone the repository.

The `javadoc` API can be generated with

    .../tensor/mvn javadoc:javadoc

Subsequently, the documentation is accessible through the file

    .../tensor/target/site/apidocs/index.html
    
## References
 
The library is used in the projects:
* `matsim-av`
* `SwissTrolley+`
* `SimBus`
* `subare`
* `owly`

