package entities;

public class Task {
    private String text;
    private Boolean isChecked;

    public Task(String text) {
        this.text = text;
        this.isChecked = false;
    }

    public String getText() {
        return this.text;
    }

    public Boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
