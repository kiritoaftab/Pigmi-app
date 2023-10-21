const COLORS = {
  primary: "#312651",
  secondary: "#444262",
  tertiary: "#FF7754",

  gray: "#83829A",
  gray2: "#EFEEE7",

  white: "#F3F4F8",
  lightWhite: "#FAFAFC",
  green:"#476C3E",

  lightBlue:"#EAFBD8",
  lightGreen:"#EAFBD8",
  lightYellow:"#F6FACD",
  

};

const FONT = {
  regular: "OpenSansRegular",
  medium: "OpenSansMedium",
  bold: "OpenSansBold",
  semiBold:"OpenSansSemiBold",
  extraBold:"OpenSansExtraBold",
  light: "OpenSansLight",
};

const SIZES = {
  xSmall: 10,
  small: 12,
  medium: 16,
  large: 20,
  xLarge: 24,
  xxLarge: 32,
};

const SHADOWS = {
  small: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 2,
  },
  medium: {
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 5.84,
    elevation: 5,
  },
};

export { COLORS, FONT, SIZES, SHADOWS };
