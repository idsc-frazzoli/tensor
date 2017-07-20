# ch.ethz.idsc.tensor

<a href="https://travis-ci.org/idsc-frazzoli/tensor"><img src="https://travis-ci.org/idsc-frazzoli/tensor.svg?branch=master" alt="Build Status"></a>

Library for tensor computation in Java 8.

Version `0.2.9`

Features:
* multi-dimensional arrays: scalars, vectors, matrices, n-linear forms, Lie-algebra ad-tensor, ...
* scalars are real, or complex numbers, or from finite fields, etc.
* values are encoded as exact fractions, or in double precision
* other projects can customize the scalars for instance to attach physical units such as `javax.measure.Unit`
* import from and export to `Mathematica`, `CSV`-, and image files

The naming of functions, as well as the string format of the expressions are inspired by Wolfram's `Mathematica`.

## Examples

Solving systems of linear equations

    Tensor matrix = Tensors.matrixInt(new int[][] { { 2, -3, 2 }, { 4, 9, -3 }, { -1, 3, 2 } });
    System.out.println(Pretty.of(Inverse.of(matrix)));

gives

    [
     [   9/37    4/37   -3/37 ]
     [ -5/111    2/37  14/111 ]
     [   7/37   -1/37   10/37 ]
    ]

singular value decomposition of given matrix

    System.out.println(Pretty.of(SingularValueDecomposition.of(matrix).getU().map(Round._4)));

gives results in machine precision

    [
     [  0.2532   0.6307  -0.7336 ]
     [ -0.9512   0.3004  -0.0700 ]
     [ -0.1763  -0.7155  -0.6760 ]
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

Functions for complex numbers

    System.out.println(Sqrt.of(RationalScalar.of(-9, 16)));

gives

    3/4*I

---

High precision

    System.out.println(Det.of(HilbertMatrix.of(8)));

gives

    1/365356847125734485878112256000000

---

Null-space

    Tensor matrix = Tensors.fromString("{{-1/3, 0, I}}");
    System.out.println(Pretty.of(NullSpace.of(matrix)));

gives

    [
     [    1     0  -I/3 ]
     [    0     1     0 ]
    ]

---

Statistics

    Distribution distribution = HypergeometricDistribution.of(10, 50, 100);
    System.out.println(RandomVariate.of(distribution, 20));

gives

    {6, 5, 1, 4, 3, 4, 7, 5, 7, 4, 6, 3, 5, 4, 5, 4, 6, 2, 6, 7}

and

    PDF pdf = PDF.of(distribution);
    System.out.println("P(X=3)=" + pdf.at(RealScalar.of(3)));

gives

    P(X=3)=84000/742729

---

Image synthesis

    int n = 251;
    Export.of(new File("image.png"), Tensors.matrix((i, j) -> //
    Tensors.of(RealScalar.of(i), RealScalar.of(j), GaussScalar.of(i + 2 * j, n), GaussScalar.of(i * j, n)), n, n));

gives

![gauss_scalar](https://cloud.githubusercontent.com/assets/4012178/26045629/63b756ee-394b-11e7-85f4-d9121905badd.png)


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
        <version>0.2.9</version>
      </dependency>
    </dependencies>

The source code is attached to the `jar` file for your convenience.

*Note*: If your IDE or maven compiler fails to download the repository automatically, you can place the binary files from the branch mvn-repo manually in the target location rooted in your user directory

    ~/.m2/repository/ch/ethz/idsc/tensor/0.2.9/*

## Optional

Clone the repository.

The `javadoc` API can be generated with

    .../tensor/mvn javadoc:javadoc

Subsequently, the documentation is accessible through the file

    .../tensor/target/site/apidocs/index.html

## References

The library is used in the projects:
* `matsim`
* `owly` and `owly3d`
* `SwissTrolley+`
* `subare`
* `QueuingNetworks`
* `SimBus`

The repository has over `950` unit tests.