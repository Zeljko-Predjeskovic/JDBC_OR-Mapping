package spengergasse.model;

import java.time.LocalDate;
import java.util.Objects;

public class Lemonade extends Persistable{

    private Long id;
    private String lemonadeName;
    private String articleNumber;
    private LocalDate expirationDate;
    private Integer producedNumber;

    public Lemonade(String lemonadeName, String articleNumber, LocalDate expirationDate, Integer producedNumber) {
        this.lemonadeName = lemonadeName;
        this.articleNumber = articleNumber;
        this.expirationDate = expirationDate;
        this.producedNumber = producedNumber;
    }

    public void setLemonadeName(String lemonadeName) {
        this.lemonadeName = lemonadeName;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setProducedNumber(Integer producedNumber) {
        this.producedNumber = producedNumber;
    }

    public String getLemonadeName() {
        return lemonadeName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Integer getProducedNumber() {
        return producedNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lemonade lemonade = (Lemonade) o;
        return lemonadeName.equals(lemonade.lemonadeName) &&
                articleNumber.equals(lemonade.articleNumber) &&
                Objects.equals(expirationDate, lemonade.expirationDate) &&
                Objects.equals(producedNumber, lemonade.producedNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lemonadeName, articleNumber, expirationDate, producedNumber);
    }

    @Override
    public String toString() {
        return "Lemonade{" +
                "lemonadeName='" + lemonadeName + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", producedNumber=" + producedNumber +
                '}';
    }
}
