package api.services.google;

public class Executor
{
  public static void main(String[] args)
  {
    DirectionsClient da = DirectionsClient.getInstance();

    String ret = da.request("Congreso 2171, Ciudad Autónoma Buenos Aires", "Medrano 951, Ciudad Autónoma Buenos Aires", DirectionsClient.JSON);
  }
}