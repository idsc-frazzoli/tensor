# ch.ethz.idsc.tensor
Library for tensor computation in Java 8.

The naming of functions is inspired by Mathematica.

Features:
* multi-dimensional arrays: scalars, vectors, matrices, trilinear forms, Lie algebra ad-tensor, ... 
* scalars are numeric values, as well as exact fractions
* other projects can customize the scalars for instance to attach physical units such as javax.measure.Unit

The library is used in the projects:
* matsim-av-eth
* SwissTrolley+
* SimBus
* jowl

## Installation
Clone the repository.

In the base folder of your local copy run

    .../tensor/mvn install

Subsequently, you can import the library in another maven project via

		<dependency>
			<groupId>ch.ethz.idsc</groupId>
			<artifactId>tensor</artifactId>
			<version>0.1.0</version>
		</dependency>

		
## Documentation
The source code is attached to the jar file.

The javadoc API can be generated with

    .../tensor/mvn javadoc:javadoc

Subsequently, the documentation is accessible through the file

    .../tensor/target/site/apidocs/index.html