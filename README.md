# ch.ethz.idsc.tensor
Library for tensor computation in Java 8.

Version `0.1.6`

Features:
* multi-dimensional arrays: scalars, vectors, matrices, n-linear forms, Lie algebra ad-tensor, ... 
* scalars are real, or complex numbers, or from finite fields, etc.
* values are encoded as exact fractions, or in double precision
* other projects can customize the scalars for instance to attach physical units such as `javax.measure.Unit`

The naming of functions, as well as the string format of the expressions are inspired by Wolfram's `Mathematica`.

## Examples 

Solving systems of linear equations

    Tensor matrix = Tensors.matrix(new Number[][] { { 4, 3 }, { 8, 2 } });
    System.out.println(Pretty.of(Inverse.of(matrix)));
    
gives

    [
     [ -1/8  3/16 ]
     [  1/2  -1/4 ]
    ]

---

Linear programming

    Tensor x = LinearProgramming.maxLessEquals( //
        Tensors.vector(1, 1), // cost
        Tensors.fromString("{{4, -1}, {2, 1}, {-5, 2}}"), // matrix
        Tensors.vector(8, 7, 2)); // rhs
    System.out.println(x);

gives

    {4/3, 13/3}

---

Tensors of rank 3

    Tensor ad = LieAlgebras.so3();
    Tensor x = Tensors.vector(7, 2, -4);
    Tensor y = Tensors.vector(-3, 5, 2);
    System.out.println(ad);
    System.out.println(ad.dot(x).dot(y)); // cross product of x and y

gives

    {{{0, 0, 0}, {0, 0, -1}, {0, 1, 0}}, {{0, 0, 1}, {0, 0, 0}, {-1, 0, 0}}, {{0, -1, 0}, {1, 0, 0}, {0, 0, 0}}}
    {24, -2, 41}

---

Scalar ops

    Scalar fraction = RationalScalar.of(-9, 16);
    System.out.println(Sqrt.of(fraction));

gives

    3/4*I

---

High precision

    System.out.println(Det.of(HilbertMatrix.of(8)));

gives

    1/365356847125734485878112256000000

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
        <version>0.1.6</version>
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

The repository has over `480` unit tests.