# -*- Makefile -*-
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#
#                               Michael A.G. Aivazis
#                        California Institute of Technology
#                        (C) 1998-2005  All Rights Reserved
#
# <LicenseText>
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

PROJECT = javagulp

#--------------------------------------------------------------------------
#

all: export-java
	BLD_ACTION="all" $(MM) recurse

tidy::
	BLD_ACTION="tidy" $(MM) recurse

clean::
	BLD_ACTION="clean" $(MM) recurse

distclean::
	BLD_ACTION="distclean" $(MM) recurse



EXPORT_JAVA_PATH = $(EXPORT_ROOT)/java
EXPORT_JAVA_PROJECT_PATH = $(EXPORT_JAVA_PATH)/$(PROJECT)


RSYNC_A = rsync -a
JAVAC = javac  # -Xlint:unchecked


JARS_DIR=../jars
JAVACLASSPATH=$(JARS_DIR)/jython.jar:$(JARS_DIR)/potentials.jar:$(JARS_DIR)/postgresql-8.3-604.jdbc3.jar:$(JARS_DIR)/j2ssh.jar


export-java:: 
	mkdir -p $(EXPORT_JAVA_PROJECT_PATH); \
	$(RSYNC_A) ./ $(EXPORT_JAVA_PROJECT_PATH); \
	cd  $(EXPORT_JAVA_PROJECT_PATH) && $(JAVAC) -classpath $(JAVACLASSPATH) `find . -name *.java` ;\


# version
# $Id: Make.mm,v 1.1.1.1 2006-11-27 00:09:14 aivazis Exp $

# End of file
