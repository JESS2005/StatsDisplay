#include <Firmata.h>
#include <LiquidCrystal.h>


int A=32;
int S=32;

byte Icons[5][8] = {

  {B00000,
  B00000,
  B01010,
  B11111,
  B01110,
  B00100,
  B00000},
  {B00000,
  B00000,
  B01000,
  B11100,
  B01100,
  B00100,
  B00000},
  {B00000,
  B00000,
  B00010,
  B00111,
  B01110,
  B01100,
  B10000},
  {B00000,
  B00000,
  B00000,
  B00000,
  B01100,
  B01100,
  B10000},
  {B11111,
  B00000,
  B00000,
  B00000,
  B00000,
  B00000,
  B00000}
};

int Vals[4];
//int N[2]={0,0};

//Vals[IconNum/2*3]

const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

/*void SetChar(int Char,int Base,int Num)
{
  Icons[Base][0]=byte(31*(Num==2)+28*(Num==1));
  lcd.createChar(Char, Icons[Base]);
}*/

void printIcons(int IconNum,int Val,int Extra)
{
  int i;
  for (i = 1; i < 11; i++) {
    if (i<=(Val/2))
    {
      if (i<=(Extra/2)) {
        lcd.write(byte(IconNum*2));
      }else if (i==((Extra+1)/2)) {
        lcd.write(byte(IconNum*2+2));
      }else {
        lcd.write(byte(IconNum*2+3));
      }
    }else if (i==((Val+1)/2))
    {
      lcd.write(byte(IconNum*2+1));
    }else if ((i<=(Extra/2))&(Extra>Val))
    {
        lcd.write(byte(IconNum*2+3));
    }else if ((i==((Extra+1)/2))&(Extra>Val))
    {
        lcd.write(byte(IconNum*2+2));
    }else
    {
      lcd.print(" ");
    }
  }

  if (Val%2==1) {
    int Line=(Extra<Val)+(Extra==Val)*2+(Extra>Val)*3;
    if (Vals[IconNum]!=Line) {
      Icons[IconNum+1][0]=byte(31*(Extra>Val)+28*(Extra==Val));
      lcd.createChar(IconNum*2+1, Icons[IconNum+1]);
      Vals[IconNum]=Line;
      //N[IconNum/2]++;
    }
  }
  if (Extra<Val) {
    if (Vals[IconNum+1]!=1) {
      Icons[IconNum][0]=byte(0);
      lcd.createChar(IconNum*2+3, Icons[IconNum]);

      Icons[IconNum][0]=byte(28);
      lcd.createChar(IconNum*2+2, Icons[IconNum]);
      Vals[IconNum+1]=1;
      //N[IconNum/2]++;
    }
  }
  if (Extra>Val) {
    if (Vals[IconNum+1]!=2) {
      Icons[4][0]=byte(31);
      lcd.createChar(IconNum*2+3, Icons[4]);

      Icons[4][0]=byte(28);
      lcd.createChar(IconNum*2+2, Icons[4]);
      Vals[IconNum+1]=2;
      
      //N[IconNum/2]++;
      //Icons[IconNum][0]=byte(31);
      //lcd.createChar(IconNum*2, Icons[IconNum]);
      }
  }
}

void AlignRight(int Row, int Num)
{
  int Max=log10(abs(Num))+1+(Num<0);
  Max+=(Max==0);
  lcd.setCursor(10,Row);
  int i;
  for (i = 0; i < 6-Max; i++) {
    lcd.print(" ");
  }
  lcd.print(Num);
}

void StringCallback(char *myString)
{
  if (myString[0]=='H')
  {
    lcd.setCursor(0, 0);
    myString[0]='0';
    printIcons(0,atoi(myString),A);
  }else if (myString[0]=='F')
  {
    lcd.setCursor(0, 1);
    //lcd.print(myString);
    myString[0]='0';
    printIcons(2,atoi(myString),S);
  }else if (myString[0]=='X')
  {
    if (myString[1]=='-') {myString[0]='-'; myString[1]='0';} else {myString[0]='0';}
    AlignRight(0,atoi(myString));
    //AlignRight(0,A);
    //lcd.setCursor(0, 0);
    //lcd.print(atoi(myString));
  }else if (myString[0]=='Z')
  {
    if (myString[1]=='-') {myString[0]='-'; myString[1]='0';} else {myString[0]='0';}
    AlignRight(1,atoi(myString));
    //AlignRight(1,S);
    //lcd.setCursor(0, 1);
    //lcd.print(atoi(myString));
  }else if (myString[0]=='A')
  {
    myString[0]='0';
    A=atoi(myString);
  }else if (myString[0]=='S')
  {
    myString[0]='0';
    S=atoi(myString);
  }else
  {
    lcd.setCursor(0, 0);
    lcd.print(myString);
  }
}


int Contrast=80;

void setup() {
  Firmata.setFirmwareVersion(0, 1);
  Firmata.attach(STRING_DATA, StringCallback);
  Firmata.begin(57600);
  pinMode(LED_BUILTIN, OUTPUT);
  analogWrite(6,Contrast);
  lcd.begin(16, 2);

  Icons[0][0]=byte(31);
  Icons[2][0]=byte(31);

  lcd.createChar(0, Icons[0]);
  lcd.createChar(1, Icons[1]);
  lcd.createChar(4, Icons[2]);
  lcd.createChar(5, Icons[3]);
  lcd.clear();
  lcd.print("Ready");
  
  //delay(500);
  //SetChar(2,1,2);
  //printIcons(2,9,3);
  //SetChar(2,1,2);

  //Icons[2][0]=byte(127);
  //lcd.createChar(2, heart);
  //Serial.begin(9600);
  //Serial.print("test");
}

void loop()
{
  
  while(Firmata.available()) { // Handles Firmata serial input
    Firmata.processInput();
  }
}

