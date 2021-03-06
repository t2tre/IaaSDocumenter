#!/bin/sh
mvn install:install-file -DgroupId=org.eclipse -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=emf -Dfile=org.eclipse.emf_2.6.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.8.0.v20150123-0452 -DartifactId=mapping -Dfile=org.eclipse.emf.mapping_2.8.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.mapping.ui_2.6.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=ecore -Dfile=org.eclipse.emf.mapping.ecore_2.6.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping -Dpackaging=jar -Dversion=2.8.0.v20150123-0452 -DartifactId=ecore2xml -Dfile=org.eclipse.emf.mapping.ecore2xml_2.8.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping.ecore2xml -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.mapping.ecore2xml.ui_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping -Dpackaging=jar -Dversion=2.8.0.v20150123-0452 -DartifactId=ecore2ecore -Dfile=org.eclipse.emf.mapping.ecore2ecore_2.8.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping.ecore2ecore -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=editor -Dfile=org.eclipse.emf.mapping.ecore2ecore.editor_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.mapping.ecore -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=editor -Dfile=org.eclipse.emf.mapping.ecore.editor_2.6.0.v20150123-0452.jar 
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.9.0.v20150123-0452 -DartifactId=importer -Dfile=org.eclipse.emf.importer_2.9.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.importer -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=rose -Dfile=org.eclipse.emf.importer.rose_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.importer -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=java -Dfile=org.eclipse.emf.importer.java_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.importer -Dpackaging=jar -Dversion=2.8.0.v20150123-0452 -DartifactId=ecore -Dfile=org.eclipse.emf.importer.ecore_2.8.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=exporter -Dfile=org.eclipse.emf.exporter_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.10.1.v20150123-0452 -DartifactId=edit -Dfile=org.eclipse.emf.edit_2.10.1.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.edit -Dpackaging=jar -Dversion=2.10.2.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.edit.ui_2.10.2.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.10.2.v20150123-0452 -DartifactId=ecore -Dfile=org.eclipse.emf.ecore_2.10.2.v20150123-0348.jar
mvn install:install-file -DgroupId=org.eclipse.emf.ecore -Dpackaging=jar -Dversion=2.10.2.v20150123-0452 -DartifactId=xmi -Dfile=org.eclipse.emf.ecore.xmi_2.10.2.v20150123-0348.jar
mvn install:install-file -DgroupId=org.eclipse.emf.ecore -Dpackaging=jar -Dversion=2.10.1.v20150123-0452 -DartifactId=editor -Dfile=org.eclipse.emf.ecore.editor_2.10.1.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.ecore -Dpackaging=jar -Dversion=2.9.0.v20150123-0452 -DartifactId=edit -Dfile=org.eclipse.emf.ecore.edit_2.9.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.ecore -Dpackaging=jar -Dversion=2.10.0.v20150123-0452 -DartifactId=change -Dfile=org.eclipse.emf.ecore.change_2.10.0.v20150123-0348.jar
mvn install:install-file -DgroupId=org.eclipse.emf.ecore.change -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=edit -Dfile=org.eclipse.emf.ecore.change.edit_2.6.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=1.3.0.v20150123-0452 -DartifactId=databinding -Dfile=org.eclipse.emf.databinding_1.3.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.databinding -Dpackaging=jar -Dversion=1.3.0.v20150123-0452 -DartifactId=edit -Dfile=org.eclipse.emf.databinding.edit_1.3.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.7.0.v20150123-0452 -DartifactId=converter -Dfile=org.eclipse.emf.converter_2.7.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.10.1.v20150123-0452 -DartifactId=common -Dfile=org.eclipse.emf.common_2.10.1.v20150123-0348.jar
mvn install:install-file -DgroupId=org.eclipse.emf.common -Dpackaging=jar -Dversion=2.9.0.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.common.ui_2.9.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.10.0.v20150123-0452 -DartifactId=codegen -Dfile=org.eclipse.emf.codegen_2.10.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.codegen -Dpackaging=jar -Dversion=2.6.0.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.codegen.ui_2.6.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.codegen -Dpackaging=jar -Dversion=2.10.2.v20150123-0452 -DartifactId=ecore -Dfile=org.eclipse.emf.codegen.ecore_2.10.2.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf.codegen.ecore -Dpackaging=jar -Dversion=2.10.2.v20150123-0452 -DartifactId=ui -Dfile=org.eclipse.emf.codegen.ecore.ui_2.10.0.v20150123-0452.jar
mvn install:install-file -DgroupId=org.eclipse.emf -Dpackaging=jar -Dversion=2.8.0.v20150123-0452 -DartifactId=ant -Dfile=org.eclipse.emf.ant_2.8.0.v20150123-0452.jar
