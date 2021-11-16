#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <SoftwareSerial.h>
SoftwareSerial NodeMcu_SoftSerial (D6, D5); //RX,TX

#define FIREBASE_HOST "solar-tracker-b5cad-default-rtdb.asia-southeast1.firebasedatabase.app"
#define FIREBASE_AUTH "WHZBgVIC43sc1VTYmE9kGfEHIzBPLCIevcLnykfQ"
#define WIFI_SSID "SPEEDY"
#define WIFI_PASSWORD "suherman"
#define ledPin D1
#define delimiter '#'

FirebaseData firebaseData;

String arrData[4];  //2 sensor

int val = 0;

void setup() {

  turnOff();

  //pin setup
  pinMode(ledPin, OUTPUT);
  
  //Open Serial Communication (Arduino-PC)
  Serial.begin(57600);

  //Open Serial Communication (Arduino-NodeMCU)
  NodeMcu_SoftSerial.begin(9600);

  //Connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {

  if (WiFi.status() == WL_CONNECTED) {
    turnOn();
    
    String data  = "";

    //baca data yang diterima
    while (NodeMcu_SoftSerial.available() > 0){
      data += char (NodeMcu_SoftSerial.read());
    }
  
    data.trim();
    Serial.println(data);

    if(data.length() != 0){
      int index = 0;
    
      for (int i = 0; i < data.length(); i++){
        if (data[i] != delimiter){
          arrData[index] += data[i];
        } else {
          index++;        
        }
      }
  
      //tampilkan di serial monitor
      Serial.println("Sensor 1 : " + arrData[0]);
      Serial.println("Sensor 2 : " + arrData[1]);
      Serial.println("Sensor 3 : " + arrData[2]);
      Serial.println("Sensor 4 : " + arrData[3]);
    
      val = random(1, 999999999999999999);
  
      for(int i = 0; i < 4; i++){
        String destination = "LIGHT_SENSOR/" + String(val) + "/LS" + String(i+1);
        if(Firebase.setString(firebaseData, destination, arrData[i])){
          Serial.println("Value uploaded succesfully");
        } else {
          Serial.println(firebaseData.errorReason());
        }      
      }
    
      //reset data
      arrData[0] = "";
      arrData[1] = "";
      arrData[2] = "";
      arrData[3] = "";        
    }
  } else {
    turnOff();
    Firebase.reconnectWiFi(true);
  }
  delay(500);
}

void turnOn() {
  digitalWrite(ledPin, HIGH);
}

void turnOff() {
  digitalWrite(ledPin, LOW);
}
