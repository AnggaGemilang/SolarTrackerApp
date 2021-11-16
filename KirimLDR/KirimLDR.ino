#include <SoftwareSerial.h>
SoftwareSerial ArduinoUno (5, 6); //RX,TX

void setup() {
  //Open Serial Communication (Arduino-PC)
  Serial.begin(57600);

  //Open Serial Communication (Arduino-NodeMCU)
  ArduinoUno.begin(9600);
}

void loop() {
  //baca nilai ldr
  int ldr1 = analogRead(0);
  int ldr2 = analogRead(1);
  int ldr3 = analogRead(2);
  int ldr4 = analogRead(3);
  String data = String(ldr1) + "#" + String(ldr2)+ "#" + String(ldr3)+ "#" + String(ldr4);
  
  //tampilkan ke serial monitor
  Serial.println(data);

  //kirim ke ESP8266  
  ArduinoUno.print(data);

  delay(2000);
}
