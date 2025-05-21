# Variables
SRC_DIR = src
BIN_DIR = EXE
DOMAIN_CLASSES = $(SRC_DIR)/DomainClasses
DRIVERS = $(SRC_DIR)/Drivers

# Compiler and flags
JAVAC = javac
JFLAGS = -d $(BIN_DIR)

# Source files
DOMAIN_CLASSES_SRC = $(DOMAIN_CLASSES)/Bag.java \
					$(DOMAIN_CLASSES)/Letter.java \
					$(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/DictionaryController.java \
					$(DOMAIN_CLASSES)/DomainController.java \
					$(DOMAIN_CLASSES)/MP_Controller.java \
					$(DOMAIN_CLASSES)/Match.java \
					$(DOMAIN_CLASSES)/Human.java \
					$(DOMAIN_CLASSES)/IA.java \
					$(DOMAIN_CLASSES)/Player.java \
					$(DOMAIN_CLASSES)/Dictionary.java \
					$(DOMAIN_CLASSES)/Board.java \
					$(DOMAIN_CLASSES)/Box.java \
					$(DOMAIN_CLASSES)/Profile.java \
					$(DOMAIN_CLASSES)/ProfileController.java \
					$(DOMAIN_CLASSES)/Dawg.java \
					$(DOMAIN_CLASSES)/Ranking.java 

# Driver classes source files
DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/DictionaryController.java \
					$(DOMAIN_CLASSES)/Dictionary.java \
					$(DOMAIN_CLASSES)/Dawg.java

PROFILE_CONTROLLER_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/ProfileController.java \
					$(DOMAIN_CLASSES)/Ranking.java \
					$(DOMAIN_CLASSES)/Profile.java

BOARDBOX_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Board.java \
					$(DOMAIN_CLASSES)/Box.java

BAG_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Bag.java \
					$(DOMAIN_CLASSES)/Letter.java

RACK_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/Bag.java \
					$(DOMAIN_CLASSES)/BagController.java \
					$(DOMAIN_CLASSES)/Letter.java

RANKING_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Ranking.java \
					$(DOMAIN_CLASSES)/Profile.java \
					$(DOMAIN_CLASSES)/ProfileController.java

PLAYER_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Profile.java \
					$(DOMAIN_CLASSES)/Human.java \
					$(DOMAIN_CLASSES)/IA.java \
					$(DOMAIN_CLASSES)/Player.java \
					$(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/Letter.java \
					$(DOMAIN_CLASSES)/Bag.java

DAWG_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Dawg.java

MY_DRIVER_CLASSES_SRC = $(DOMAIN_CLASSES)/Bag.java \
					$(DOMAIN_CLASSES)/Letter.java \
					$(DOMAIN_CLASSES)/Rack.java \
					$(DOMAIN_CLASSES)/DictionaryController.java \
					$(DOMAIN_CLASSES)/MP_Controller.java \
					$(DOMAIN_CLASSES)/Match.java \
					$(DOMAIN_CLASSES)/Human.java \
					$(DOMAIN_CLASSES)/IA.java \
					$(DOMAIN_CLASSES)/Player.java \
					$(DOMAIN_CLASSES)/Dictionary.java \
					$(DOMAIN_CLASSES)/Board.java \
					$(DOMAIN_CLASSES)/Box.java \
					$(DOMAIN_CLASSES)/Profile.java \
					$(DOMAIN_CLASSES)/ProfileController.java \
					$(DOMAIN_CLASSES)/Dawg.java \
					$(DOMAIN_CLASSES)/Ranking.java \


# Driver source files
					
PROFILE_CONTROLLER_DRIVER_SRC = $(DRIVERS)/ProfileControllerDriver.java
BOARDBOX_DRIVER_SRC = $(DRIVERS)/BoardBoxDriver.java
BAG_DRIVER_SRC = $(DRIVERS)/BagDriver.java
RACK_DRIVER_SRC = $(DRIVERS)/RackDriver.java
DICTIONARY_CONTROLLER_DRIVER_SRC = $(DRIVERS)/DictionaryControllerDriver.java
PLAYER_DRIVER_SRC = $(DRIVERS)/PlayerDriver.java
RANKING_DRIVER_SRC = $(DRIVERS)/RankingDriver.java
PLAYER_DRIVER_SRC = $(DRIVERS)/PlayerDriver.java
DAWG_DRIVER_SRC = $(DRIVERS)/DawgDriver.java
MY_DRIVER_SRC = $(DRIVERS)/MyDriver.java


# Targets
# Compilar todos los drivers
all: BagDriver RackDriver DictionaryControllerDriver ProfileControllerDriver BoardBoxDriver RankingDriver PlayerDriver

MainDriver: $(DOMAIN_CLASSES_SRC) $(DOMAIN_CLASSES)/Driver.java
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC) $(DOMAIN_CLASSES)/Driver.java 

# Compilar los drivers individualmente
ProfileControllerDriver: $(PROFILE_CONTROLLER_DRIVER_CLASSES_SRC) $(PROFILE_CONTROLLER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(PROFILE_CONTROLLER_DRIVER_CLASSES_SRC) $(PROFILE_CONTROLLER_DRIVER_SRC)

BoardBoxDriver: $(BOARDBOX_DRIVER_CLASSES_SRC) $(BOARDBOX_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(BOARDBOX_DRIVER_CLASSES_SRC) $(BOARDBOX_DRIVER_SRC)

BagDriver: $(BAG_DRIVER_CLASSES_SRC) $(BAG_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(BAG_DRIVER_CLASSES_SRC) $(BAG_DRIVER_SRC)

RackDriver: $(RACK_DRIVER_CLASSES_SRC) $(RACK_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(RACK_DRIVER_CLASSES_SRC) $(RACK_DRIVER_SRC)

DictionaryControllerDriver: $(DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DICTIONARY_CONTROLLER_DRIVER_CLASSES_SRC) $(DICTIONARY_CONTROLLER_DRIVER_SRC)

RankingDriver: $(RANKING_DRIVER_CLASSES_SRC) $(RANKING_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(RANKING_DRIVER_CLASSES_SRC) $(RANKING_DRIVER_SRC)

PlayerDriver: $(PLAYER_DRIVER_CLASSES_SRC) $(PLAYER_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(PLAYER_DRIVER_CLASSES_SRC) $(PLAYER_DRIVER_SRC)

DawgDriver: $(DAWG_DRIVER_CLASSES_SRC) $(DAWG_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(DAWG_DRIVER_CLASSES_SRC) $(DAWG_DRIVER_SRC)

MyDriver: $(MY_DRIVER_CLASSES_SRC) $(MY_DRIVER_SRC)
	$(JAVAC) $(JFLAGS) $(MY_DRIVER_CLASSES_SRC) $(MY_DRIVER_SRC)

# Compilar solo las clases de DomainClasses
DomainClasses: $(BIN_DIR) $(DOMAIN_CLASSES_SRC)
	$(JAVAC) $(JFLAGS) $(DOMAIN_CLASSES_SRC)


run-main: MainDriver
	java -cp $(BIN_DIR) $(DOMAIN_CLASSES)/Driver

# Ejecutar drivers
run-BagDriver: BagDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/BagDriver
run-RackDriver: RackDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/RackDriver
run-ProfileControllerDriver: ProfileControllerDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/ProfileControllerDriver
run-BoardBoxDriver: BoardBoxDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/BoardBoxDriver
run-DictionaryControllerDriver: DictionaryControllerDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/DictionaryControllerDriver

run-RankingDriver: RankingDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/RankingDriver

run-PlayerDriver: PlayerDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/PlayerDriver

run-DawgDriver: DawgDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/DawgDriver

run-MyDriver: MyDriver
	java -cp $(BIN_DIR) $(DRIVERS_DIR)/MyDriver

# Borrar los archivos compilados

clean:
	rm -rf $(BIN_DIR)/*.class