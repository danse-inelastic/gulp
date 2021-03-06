<project name="Javagulp" default="jar-for-danse" basedir=".">

	<macrodef name="unsignjar">

		<attribute name="jar" />

		<sequential>
			<!-- Remove any existing signatures from a JAR file. -->
			<tempfile prefix="usignjar-" destdir="${java.io.tmpdir}" property="temp.file" />
			<echo message="Removing signatures from JAR: @{jar}" />
			<mkdir dir="${temp.file}" />

			<unjar src="@{jar}" dest="${temp.file}">
				<patternset>
					<include name="**" />
					<exclude name="META-INF/*.SF" />
					<exclude name="META-INF/*.DSA" />
					<exclude name="META-INF/*.RSA" />
				</patternset>
			</unjar>
			<delete file="@{jar}" failonerror="true" />
			<!-- Touch it in case the file didn't have a manifest.
	             Otherwise the JAR task below will fail if the manifest 
		     file doesn't exist. -->
			<mkdir dir="${temp.file}/META-INF" />
			<touch file="${temp.file}/META-INF/MANIFEST.MF" />
			<jar destfile="@{jar}" basedir="${temp.file}" includes="**" manifest="${temp.file}/META-INF/MANIFEST.MF" />
			<delete dir="${temp.file}" failonerror="true" />
		</sequential>
	</macrodef>

	<description>GULP</description>

	<property environment="env" />
	<property name="src" location="src" />
	<property name="trueblueDeploy" location="/var/www/java"/>
	<property name="lib" value="lib" />
	<property name="app-name" value="GULP" />
	<property name="username" value="jbk" />
	<property name="build" location="${env.EXPORT_ROOT}/java/javagulp/" />
	<property name="vnfDeploy" location="${env.EXPORT_ROOT}/vnf/html/java"/>
	<property name="keystore" location="${env.EXPORT_ROOT}/vnf/html/java/vnfKeys" />

	<path id="classpath" path="${classpath}">
		<fileset dir="${lib}">
			<include name="**/*.jar" />
		</fileset>
	</path>

	<target name="init">
		<tstamp>
			<format property="now" pattern="MMMM d, yyyy hh:mm aa" />
			<format property="webappnow" pattern="h:mm a EEE, MMM d, ''yy" />
		</tstamp>
	</target>

	<target name="jar-potentials" description="puts potentials in a jar" depends="init">
		<jar destfile="lib/potentials.jar">
			<fileset dir="resources"/>
		</jar>
		<!-- sign jar -->
		<signjar jar="lib/potentials.jar" keystore="${keystore}" alias="vnf" storepass="purpl3" keypass="purpl3" />
		<copy file="lib/potentials.jar" todir="deployable"/>
	</target>
	
	<target name="sign-libraries" description="sign all the libraries gulp is dependent on" depends="init">
		<unsignjar jar="deployable/commons-logging.jar" />
		<unsignjar jar="deployable/j2ssh.jar" />
		<unsignjar jar="deployable/pg74.216.jdbc3.jar" />
		<signjar jar="deployable/commons-logging.jar" keystore="${keystore}" alias="vnf" storepass="purpl3" keypass="purpl3" />
		<signjar jar="deployable/j2ssh.jar" keystore="${keystore}" alias="vnf" storepass="purpl3" keypass="purpl3" />
		<signjar jar="deployable/pg74.216.jdbc3.jar" keystore="${keystore}" alias="vnf" storepass="purpl3" keypass="purpl3" />
	</target>

	<target name="jar-for-danse" description="build jars for vnf" depends="init">
		<!-- jar up the source and create the manifest
		<jar jarfile="deployable/${app-name}.jar">
			<manifest file="jee/MANIFEST.MF" />
			<fileset dir="${build}">
				<include name="javagulp/**" />
			</fileset>
		</jar>-->
		<delete file="deployable/${app-name}.jar"/>
		<delete file="MANIFEST.MF"/>
		<manifest file="MANIFEST.MF">
			<attribute name="Main-Class" value="javagulp.controller.LaunchAtomSim"/>
		</manifest>
		<jar destfile="deployable/${app-name}.jar" manifest="MANIFEST.MF">
			<fileset dir="${build}"/>
		</jar>
		<!-- sign all jars -->
		<signjar jar="deployable/${app-name}.jar" keystore="${keystore}" alias="vnf" storepass="purpl3" keypass="purpl3" />
		<!-- copy the libraries to deployable -->
		<copy file="lib/commons-logging.jar" todir="deployable"/>
		<copy file="lib/j2ssh.jar" todir="deployable"/>
		<copy file="lib/pg74.216.jdbc3.jar" todir="deployable"/>
		<copy todir="${vnfDeploy}">
			<fileset dir="deployable"/>
		</copy>
	</target>


	<target name="copyToTrueblue" depends="jar-for-danse">
		<copy todir="${trueblueDeploy}">
			<fileset dir="deployable"/>
		</copy>
	</target>

	<!--<project name="Deploy From Eclipse to Tomcat" basedir=".">
	<property name="warfile" value="sanfran"/>-->
	<target name="unpack">
		<unwar src="${warfile}.war" dest="${warfile}" />
	</target>

	<target name="create">
		<war destfile="${warfile}.war" webxml="WebContent/WEB-INF/web.xml" update="true">
			<classes dir="build\classes" />
			<fileset dir="WebContent">
				<exclude name="WEB-INF/web.xml" />
			</fileset>
		</war>
	</target>

	<target name="copy">
		<copy todir="c:\tomcat5517\webapps" overwrite="true">
			<fileset dir=".">
				<include name="*.war" />
			</fileset>
		</copy>
	</target>

	<target name="deploy">
		<antcall target="create" />
		<antcall target="copy" />
	</target>



	<target name="clean">
		<delete dir="bin" />
	</target>

</project>
