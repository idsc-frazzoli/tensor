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

## Include in your project

Modify the pom file of you project to specify repository and dependency of the tensor library:

	</repositories>
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
			<version>0.1.0</version>
		</dependency>
	</dependencies>

The source code is attached to the jar file for your convenience.

	
## Optional

Clone the repository.

The javadoc API can be generated with

    .../tensor/mvn javadoc:javadoc

Subsequently, the documentation is accessible through the file

    .../tensor/target/site/apidocs/index.html