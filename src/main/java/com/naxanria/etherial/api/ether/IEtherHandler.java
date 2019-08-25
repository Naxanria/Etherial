package com.naxanria.etherial.api.ether;

public interface IEtherHandler
{
  int getCapacity();
  int getAmount();
  void setAmount(int newAmount);
  
  default int add(int amount)
  {
    return add(amount, false);
  }
  
  default int add(int amount, boolean simulate)
  {
    int current = getAmount();
    int diff = getCapacity() - current;
    int tot = (diff < amount) ? diff : amount;
    if (!simulate)
    {
      setAmount(current + tot);
    }
    
    return amount - tot;
  }
  
  default int subtract(int amount)
  {
    return subtract(amount, false);
  }
  
  default int subtract(int amount, boolean simulate)
  {
    int current = getAmount();
    int diff = current - amount;
    if (diff < 0)
    {
      diff = current;
    }
    
    if (!simulate)
    {
      setAmount(current - diff);
    }
    
    return diff;
  }
  
  default float progress()
  {
    return getAmount() / (float) getCapacity();
  }
}
