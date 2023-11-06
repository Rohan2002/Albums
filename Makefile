SRC_DIR = src/photos/model

BIN_DIR = bin

SOURCES = $(wildcard $(SRC_DIR)/*.java)

JAVAC = javac
JAVAC_FLAGS = -d $(BIN_DIR)

MAIN_CLASS = photos.model.Photos
CUSTOM_CLASS ?= MAIN_CLASS

all: $(BIN_DIR) compile

compile:
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCES)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

run:
	java -cp $(BIN_DIR) $(MAIN_CLASS)

run_custom:
	java -cp $(BIN_DIR) photos.$(CUSTOM_CLASS)

clean:
	rm -rf $(BIN_DIR)

.PHONY: all clean compile
 