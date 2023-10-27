export const  formatIndianRupee = (number) => {
    if (isNaN(number) || !isFinite(number)) {
      return "Invalid";
    }
    const formattedRupees = number.toLocaleString("en-IN");
    return formattedRupees;
  }