#define ledPin 3
#define btnPin 7
#define potensionPin A4
#define lightModulePin A0

int outputPotensio = 0;
int btnState = 0;
int outputCahaya = 0;

void setup() {
  Serial.begin(115200);
  pinMode(ledPin, OUTPUT);
  pinMode(btnPin, INPUT);
}

void loop() {

// Push Button

//  btnState = digitalRead(btnPin);
//  if(btnState == HIGH){
//    digitalWrite(ledPin, HIGH);
//  } else {
//    digitalWrite(ledPin, LOW);
//  }

// Potensio

//  outputPotensio = map(analogRead(potensionPin), 0, 1023, 0, 255);
//  analogWrite(ledPin, outputPotensio);

// Sensor Cahaya
  
//  outputCahaya = analogRead(lightModulePin);
//  Serial.println(outputCahaya);


  Serial.println("Halo ini dari esp");
  
}
