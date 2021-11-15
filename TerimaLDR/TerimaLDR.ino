#include <SoftwareSerial.h>
SoftwareSerial NodeMcu_SoftSerial (D6, D5); //RX,TX

String arrData[4];  //2 sensor

void setup() {
  //Open Serial Communication (Arduino-PC)
  Serial.begin (57600);

  //Open Serial Communication (Arduino-NodeMCU)
  NodeMcu_SoftSerial.begin(9600);
}

void loop() {

  String data  = "";

  //baca data yang diterima
  while (NodeMcu_SoftSerial.available() > 0){
    data += char (NodeMcu_SoftSerial.read());
  }

  data.trim();
  Serial.println(data);

  int index = 0;

  for (int i = 0; i < data.length(); i++){
    char delimiter = '#';  //pemisah
    if (data[i] != delimiter)
      arrData[index] += data[i];
    else
      index++;

  }
  
  //tampilkan di serial monitor
  Serial.println("Sensor 1 : " + arrData[0]);
  Serial.println("Sensor 2 : " + arrData[1]);
  Serial.println("Sensor 3 : " + arrData[2]);
  Serial.println("Sensor 4 : " + arrData[3]);

  //reset data
  arrData[0] = "";
  arrData[1] = "";
  arrData[2] = "";
  arrData[3] = "";
  
  delay(1000);
}
