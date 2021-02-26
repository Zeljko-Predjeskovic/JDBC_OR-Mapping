package spengergasse.model;

import java.util.Objects;

public class Tea extends Persistable{

    private String teeName;
    private int caffeinInMiligramm;

    public Tea(String teeName, int caffeinInMiligramm) {
        this.teeName = teeName;
        this.caffeinInMiligramm = caffeinInMiligramm;
    }

    public String getTeeName() {
        return teeName;
    }

    public void setTeeName(String teeName) {
        this.teeName = teeName;
    }

    public int getCaffeinInMilligramm() {
        return caffeinInMiligramm;
    }

    public void setCaffeinInMilligramm(int caffeinInMilligramm) {
        this.caffeinInMiligramm = caffeinInMilligramm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tea tea = (Tea) o;
        return caffeinInMiligramm == tea.caffeinInMiligramm &&
                Objects.equals(teeName, tea.teeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teeName, caffeinInMiligramm);
    }
}
