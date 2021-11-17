#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <SoftwareSerial.h>
#include <NTPClient.h>
#include <WiFiUdp.h>
SoftwareSerial NodeMcu_SoftSerial (D6, D5); //RX,TX

#define FIREBASE_HOST "solar-tracker-b5cad-default-rtdb.asia-southeast1.firebasedatabase.app"
#define FIREBASE_AUTH "WHZBgVIC43sc1VTYmE9kGfEHIzBPLCIevcLnykfQ"
#define WIFI_SSID "SPEEDY"
#define WIFI_PASSWORD "suherman"
#define ledPin D1
#define delimiter '#'

FirebaseData firebaseData;

const long utcOffsetInSeconds = 25200;

WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "id.pool.ntp.org", utcOffsetInSeconds);

String arrData[4];  //2 sensor

int counterID = 1;
int statusBaris = false;

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

  timeClient.begin();
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    turnOn();

    hapusData();
    
    String data  = "";

    //baca data yang diterima
    while (NodeMcu_SoftSerial.available() > 0){
      data += char (NodeMcu_SoftSerial.read());
    }
  
    data.trim();

    int index = 0;
    
    for (int i = 0; i < data.length(); i++){
      if (data[i] != delimiter){
        arrData[index] += data[i];
      } else {
        index++;        
      }
    }

    if(arrData[0].length() != 0 && arrData[1].length() != 0 && arrData[2].length() != 0 && arrData[3].length() != 0){
      
      Serial.println(data);

      String target = "";

      unsigned long epochTime = timeClient.getEpochTime();
      struct tm *ptm = gmtime ((time_t *)&epochTime);
    
      timeClient.update();

      int monthDay = ptm->tm_mday;
      int currentMonth = ptm->tm_mon+1;
      int currentYear = ptm->tm_year+1900;
    
      target = "LIGHT_SENSOR/list_data_" + String(counterID) + "/created_at";
    
      String currentDate = String(currentYear) + "-" + String(currentMonth) + "-" + String(monthDay) + " " + String(timeClient.getFormattedTime());
      if(Firebase.setString(firebaseData, target, currentDate)){
        Serial.println(currentDate + "uploaded succesfully");
      } else {
        Serial.println(firebaseData.errorReason());
      }      
  
      for(int i = 0; i < 4; i++){
        target = "LIGHT_SENSOR/list_data_" + String(counterID) + "/LS" + String(i+1);
        int data = arrData[i].toInt();
        if(Firebase.setInt(firebaseData, target, data)){
          Serial.println("LS" + String(i+1) + " uploaded succesfully");        
        } else {
          Serial.println(firebaseData.errorReason());
        }      
      }
      
      counterID++;

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

void hapusData() {
  if(statusBaris == false){
    if(counterID == 21){
      for(int i = 1; i <= 10; i++){
        String target = "LIGHT_SENSOR/list_data_" + String(i);
        Firebase.deleteNode(firebaseData, target);
      }
      counterID = 1;
      statusBaris = true;
    }
  } else {
    if(counterID == 11){
      for(int i = 11; i <= 20; i++){
        String target = "LIGHT_SENSOR/list_data_" + String(i);
        Firebase.deleteNode(firebaseData, target);
      }
      counterID = 11;
      statusBaris = false;
    }        
  }
}
