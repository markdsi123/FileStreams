import javax.swing.*;

public class Product {
    private String name;
    private String desc;
    private String ID;
    private double cost;

    public Product(String name, String desc, String ID, double cost) {
        this.name = String.format("%-35s", name);
        this.desc = String.format("%75s", desc);
        this.ID = String.format("%-6s", ID);
        this.cost = cost;
    }
    public static boolean isValid(JTextField nameTF, JTextField descTF, JTextField idTF, JTextField priceTF)
    {
        String name = nameTF.getText();
        String desc = descTF.getText();
        String id = idTF.getText();
        if(name.length()<=35 && desc.length() <= 75 && id.length() <= 6 && priceTF != null )
        {
            return true;
        }else {
            return false;
        }
    }

    public static String namePadding(String name)
    {
        do
        {
            name+=" ";
        }while(name.length()<35);
        return name;
    }
    public static String descPadding(String desc)
    {
        do
        {
            desc+=" ";
        }while(desc.length()<75);
        return desc;
    }
    public static String idPadding(String id)
    {
        do
        {
            id+=" ";
        }while(id.length()<6);
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public String toCSVDataRecord()
    {
        return ID + ", " + name + ", " + desc + ", " + cost;
    }

}