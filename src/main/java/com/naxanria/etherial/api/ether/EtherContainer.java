package com.naxanria.etherial.api.ether;

public class EtherContainer implements IEtherHandler
{
  protected int capacity;
  protected int amount;
  
  @Override
  public int getCapacity()
  {
    return capacity;
  }
  
  @Override
  public int getAmount()
  {
    return amount;
  }
  
  @Override
  public void setAmount(int newAmount)
  {
  
  }
}
