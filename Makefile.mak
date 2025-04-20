# Variables
SRC_DIR = src
BIN_DIR = bin
DOMAIN_CLASSES = $(SRC_DIR)/DomainClasses
DRIVERS = $(SRC_DIR)/Drivers

# Compiler and flags
JAVAC = javac
JFLAGS = -d $(BIN_DIR)

# Source files
DOMAIN_CLASSES_SRC = $(DOMAIN_CLASSES)/BagController.java \
                     $(DOMAIN_CLASSES)/Bag.java \
                     $(DOMAIN_CLASSES)/Letter.java \
                     $(DOMAIN_CLASSES)/Rack.java

BAG_DRIVER_SRC = $(DRIVERS)/BagDriver.java
RACK_DRIVER_SRC = $(DRIVERS)/RackDriver.java

# Targets
all: BagDriver RackDriver

BagDriver: $(DOMAIN_CLASSES_SRC) $(BAG_DRIVER_SRC)
    $(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(BAG_DRIVER_SRC)

RackDriver: $(DOMAIN_CLASSES_SRC) $(RACK_DRIVER_SRC)
    $(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(RACK_DRIVER_SRC)

clean:
    rm -rf $(BIN_DIR)/*.class