# ch.ethz.idsc.tensor

<a href="https://travis-ci.org/idsc-frazzoli/tensor"><img src="https://travis-ci.org/idsc-frazzoli/tensor.svg?branch=master" alt="Build Status"></a>

Library for tensor computations in Java 8.

Version `0.4.8`

The tensor library was developed with the following objectives in mind
* support for exact precision using integer fractions
* support for calculation with physical units
* suitable for use in safety-critical real-time systems
* API and string expressions inspired by `Mathematica`

## Features

* multi-dimensional arrays: scalars, vectors, matrices, n-linear forms, Lie-algebra ad-tensor, ...
* scalars are real-, or complex numbers, from finite fields, or quantities with physical units
* values are encoded as exact integer fractions, in double precision, and as `java.math.BigDecimal`
* probability distributions for random variate generation: Binomial-, Poisson-, Exponential-distribution etc.
* import from and export to `Mathematica`, `CSV`-, and image files

## Gallery

<table>
<tr>
<td>

![gammademo](https://user-images.githubusercontent.com/4012178/28755698-bdb96546-7560-11e7-88d5-2d143e155e75.png)

Gamma

<td>

![inversetrigdemo2](https://user-images.githubusercontent.com/4012178/28755697-bdb72d58-7560-11e7-8a70-3ef9d82ff48c.png)

Trigonometry

<td>

![mandelbulbdemo](https://user-images.githubusercontent.com/4012178/28755696-bd98789a-7560-11e7-8ebc-001c37f0a4fd.png)

Nylander's formula

<td>

![newtondemo](https://user-images.githubusercontent.com/4012178/35206180-22bed070-ff3b-11e7-8def-407345e3693e.png)

Newton's method

</tr>
</table>

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

The tensor library implements `Quantity`, i.e. numbers with physical units.
Several algorithms are verified to work with scalars of type `Quantity`.

    Tensor matrix = Tensors.fromString( //
      "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}");
    CholeskyDecomposition cd = CholeskyDecomposition.of(matrix);
    System.out.println(cd.diagonal());
    System.out.println(Pretty.of(cd.getL()));
    System.out.println(cd.det().divide(Quantity.of(20, "m^2*rad")));

gives

    {60[m^2], 5[rad^2], 1/3[kg^2]}
    [
     [             1              0              0 ]
     [ 1/2[m^-1*rad]              1              0 ]
     [  1/3[kg*m^-1]   1[kg*rad^-1]              1 ]
    ]
    5[kg^2*rad]

The arithmetic for the scalar type `Quantity` was developed in collaboration with the project `Swisstrolley+`.

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

Indices for the `set` and `get` functions start from zero like in C/Java:

    Tensor matrix = Array.zeros(3, 4);
    matrix.set(Tensors.vector(9, 8, 4, 5), 2);
    matrix.set(Tensors.vector(6, 7, 8), Tensor.ALL, 1);
    System.out.println(Pretty.of(matrix));

gives

    [
     [ 0  6  0  0 ]
     [ 0  7  0  0 ]
     [ 9  8  4  5 ]
    ]

Extraction of the 4th column

    System.out.println(matrix.get(Tensor.ALL, 3));

gives the vector

    {0, 0, 5}

---

Tensors of rank 3

    Tensor ad = LieAlgebras.so3();
    Tensor x = Tensors.vector(7, 2, -4);
    Tensor y = Tensors.vector(-3, 5, 2);
    System.out.println(ad);
    System.out.println(ad.dot(x).dot(y)); // coincides with cross product of x and y

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
    Export.of(new File("image.png"), Tensors.matrix((i, j) -> Tensors.of( //
      RealScalar.of(i), RealScalar.of(j), GaussScalar.of(i + 2 * j, n), GaussScalar.of(i * j, n)), n, n));

gives

![gauss_scalar](https://cloud.githubusercontent.com/assets/4012178/26045629/63b756ee-394b-11e7-85f4-d9121905badd.png)

---

Several functions support evaluation to higher than machine precision for type `DecimalScalar`.

    System.out.println(Exp.of(DecimalScalar.of(10)));
    System.out.println(Sqrt.of(DecimalScalar.of(2)));

gives

    220255.6579480671651695790064528423`34
    1.414213562373095048801688724209698`34

The number after the prime indicates the precision of the decimal.
The string representation is compatible with `Mathematica`.

---

The units of a quantity are chosen by the application layer.
For instance, `Quantity.of(3, "Apples")` is valid syntax.

The tensor library contains the resource `/unit/si.properties` that encodes the SI unit system in the familiar strings such as `m`, `kg`, `s`, but the use of this convention is optional.
The example below makes use of these provided definitions

    Scalar mass = Quantity.of(300, "g"); // in gram
    Scalar a = Quantity.of(981, "cm*s^-2"); // in centi-meters per seconds square
    Scalar force = mass.multiply(a);
    System.out.println(force);
    Scalar force_N = UnitConvert.SI().to(Unit.of("N")).apply(force);
    System.out.println(force_N);

gives

    294300[cm*g*s^-2]
    2943/1000[N]

---

Predefined color gradients

![colordatagradients](https://user-images.githubusercontent.com/4012178/35206196-39acb66c-ff3b-11e7-9db8-8590a2ee2777.png)


## Integration

Specify `repository` and `dependency` of the tensor library in the `pom.xml` file of your maven project:

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
        <version>0.4.8</version>
      </dependency>
    </dependencies>

The source code is attached to every release.

> *Note*: If your IDE or maven compiler fails to download the repository automatically, you can place the binary files from the branch mvn-repo manually in the target location rooted in your user directory

    ~/.m2/repository/ch/ethz/idsc/tensor/0.4.8/*

## Documentation

The source code is documented.
The `javadoc` API is generated with

    .../tensor/mvn javadoc:javadoc

The documentation is accessible through the file

    .../tensor/target/site/apidocs/index.html

## References

The library is used in the projects:
* `matsim`, and `queuey` 
* `subare`
* `SwissTrolley+` that implements Scalar with physical units from `javax.measure.Unit`
* `owl`, and `owly3d`
* `retina`
* `lcm-java`

The repository has over `1950` unit tests.