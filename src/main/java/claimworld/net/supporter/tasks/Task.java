package claimworld.net.supporter.tasks;

public class Task {
    private final String name;
    private final String data;
    private final int number;

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public int getNumber() {
        return number;
    }

    public Task(String name, String data, int number) {
        this.name = name;
        this.data = data;
        this.number = number;
    }
}
