/* Group Members:
 *  Zeeshan Shams 1100655
 * Anas Ahmed 1097522
 * Hussain Ujjainwala 1097500
 */
package STUDY;

import java.util.ArrayList;
import java.util.Scanner;

// anything that can print a short summary of itself
interface Reportable {
    String getSummary();
}

// our own exceptions, thrown when a rule of the system is broken
class ClubFullException extends Exception {
    ClubFullException(String msg) {
        super(msg);
    }
}

class EventFullException extends Exception {
    EventFullException(String msg) {
        super(msg);
    }
}

class DuplicateException extends Exception {
    DuplicateException(String msg) {
        super(msg);
    }
}

class NotFoundException extends Exception {
    NotFoundException(String msg) {
        super(msg);
    }
}

// one student,keeps the events they joined and the club types they got stamps for - expo passport type
class Student {
    private int id;
    private String name;
    private String major;
    private ArrayList<String> stamps = new ArrayList<>();
    private ArrayList<Event> history = new ArrayList<>();

    Student(int id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getMajor() {
        return major;
    }

    ArrayList<String> getStamps() {
        return stamps;
    }

    ArrayList<Event> getHistory() {
        return history;
    }

    // saves the event and gives a stamp if the club type is new for this student
    void addEvent(Event e) {
        history.add(e);
        String category = e.getClub().getCategory();
        if (!stamps.contains(category)) {
            stamps.add(category);
        }
    }
}

// parent of all club types, the subclasses add their own extra detail -
abstract class Club implements Reportable {
    private String name;
    private String category;
    private String advisor;
    private int maxMembers;
    private ArrayList<Student> members = new ArrayList<>();

    Club(String name, String category, String advisor, int maxMembers) {
        this.name = name;
        this.category = category;
        this.advisor = advisor;
        this.maxMembers = maxMembers;
    }

    String getName() {
        return name;
    }

    String getCategory() {
        return category;
    }

    int getMemberCount() {
        return members.size();
    }

    // stops duplicates and joining a full club - can be increased from editing the club by the user
    void addMember(Student s) throws ClubFullException, DuplicateException {
        if (members.contains(s)) {
            throw new DuplicateException(s.getName() + " is already in " + name);
        }
        if (members.size() >= maxMembers) {
            throw new ClubFullException(name + " is full (" + maxMembers + " members max)");
        }
        members.add(s);
    }

    void setName(String name) {
        this.name = name;
    }

    void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    // cannot go below the number of members already in the club
    void setMaxMembers(int maxMembers) {
        if (maxMembers <= 0) {
            throw new IllegalArgumentException("Maximum members must be more than 0");
        }
        if (maxMembers < members.size()) {
            throw new IllegalArgumentException("The club already has " + members.size() + " members");
        }
        this.maxMembers = maxMembers;
    }

    abstract String getActivityInfo();

    abstract String getActivityLabel();

    abstract void setActivityInfo(String value);

    public String getSummary() {
        return name + " [" + category + "] advisor: " + advisor + ", members: "
                + members.size() + "/" + maxMembers + ", " + getActivityInfo();
    }
}

// each club type below sets its own category and extra field - can also be edited
class TechnologyClub extends Club {
    private String mainLanguage;

    TechnologyClub(String name, String advisor, int max, String mainLanguage) {
        super(name, "Technology", advisor, max);
        this.mainLanguage = mainLanguage;
    }

    String getActivityInfo() {
        return "main language: " + mainLanguage;
    }

    String getActivityLabel() {
        return "Main programming language";
    }

    void setActivityInfo(String value) {
        mainLanguage = value;
    }
}

class SportsClub extends Club {
    private String sport;

    SportsClub(String name, String advisor, int max, String sport) {
        super(name, "Sports", advisor, max);
        this.sport = sport;
    }

    String getActivityInfo() {
        return "sport: " + sport;
    }

    String getActivityLabel() {
        return "Sport";
    }

    void setActivityInfo(String value) {
        sport = value;
    }
}

class ArtsClub extends Club {
    private String artForm;

    ArtsClub(String name, String advisor, int max, String artForm) {
        super(name, "Arts", advisor, max);
        this.artForm = artForm;
    }

    String getActivityInfo() {
        return "art form: " + artForm;
    }

    String getActivityLabel() {
        return "Art form";
    }

    void setActivityInfo(String value) {
        artForm = value;
    }
}

class VolunteerClub extends Club {
    private int hoursPerSemester;

    VolunteerClub(String name, String advisor, int max, int hoursPerSemester) {
        super(name, "Volunteer", advisor, max);
        this.hoursPerSemester = hoursPerSemester;
    }

    String getActivityInfo() {
        return "required hours: " + hoursPerSemester;
    }

    String getActivityLabel() {
        return "Required volunteer hours";
    }

    void setActivityInfo(String value) {
        try {
            int hours = Integer.parseInt(value);
            if (hours <= 0) {
                throw new IllegalArgumentException("Hours must be more than 0");
            }
            hoursPerSemester = hours;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hours must be a whole number");
        }
    }
}

// an event of one club,with the list of students registered for it
class Event implements Reportable {
    private int id;
    private String name;
    private String date;
    private String venue;
    private int capacity;
    private Club club;
    private ArrayList<Student> participants = new ArrayList<>();

    Event(int id, String name, String date, String venue, int capacity, Club club) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.club = club;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Club getClub() {
        return club;
    }

    boolean isFull() {
        return participants.size() >= capacity;
    }

    // checks duplicate and capacity before adding the student
    void register(Student s) throws EventFullException, DuplicateException {
        if (participants.contains(s)) {
            throw new DuplicateException(s.getName() + " is already registered for " + name);
        }
        if (isFull()) {
            throw new EventFullException(name + " is full");
        }
        participants.add(s);
        s.addEvent(this);
    }

    public String getSummary() {
        String text = "Event #" + id + ": " + name + " (" + club.getName() + ") on " + date
                + " at " + venue + " - " + participants.size() + "/" + capacity + " registered";
        for (Student s : participants) {
            text += "\n  " + s.getName() + " (" + s.getId() + ")";
        }
        return text;
    }
}

public class CampusConnect_Sec1_G2 {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Club> clubs = new ArrayList<>();
    static ArrayList<Event> events = new ArrayList<>();
    static String[] categories = {"Technology", "Sports", "Arts", "Volunteer"};

    public static void main(String[] args) {

        loadDefaultClubs();
        int userChoice;
        do {
            DisplayMenu();
            userChoice = UserMenuChoice();
            switch (userChoice) {
                case 1:
                    RegisterStudent();
                    break;
                case 2:
                    CreateClub();
                    break;
                case 3:
                    EditClub();
                    break;
                case 4:
                    CreateEvent();
                    break;
                case 5:
                    RegisterStudentForEvent();
                    break;
                case 6:
                    ViewParticipationReports();
                    break;
                case 7:
                    NewExtraFunctionality();
                    break;
                case 8:
                    BonusFunctionality();
                    break;
                case 0:
                    System.out.println("Thank you for using CSC301's CampusConnect - Student Club & Event Management System! Keep Smiling!");
                    break;
            }
        } while (userChoice != 0);
    }

    public static void DisplayMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("CampusConnect - Student Club & Event Management System (Fall 2026)");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Register a new student");
        System.out.println("2. Create a new club");
        System.out.println("3. Edit club details");
        System.out.println("4. Create a new event for a club");
        System.out.println("5. Register a student for an event");
        System.out.println("6. View participation reports");
        System.out.println("7. Campus Passport (collect a stamp from every club type)");
        System.out.println("8. Join a club");
        System.out.println("0. Exit");
        System.out.println("---------------------------------------------------------------");
    }

    public static int UserMenuChoice() {
        int choice;
        do {
            choice = readInt("Your Choice (0-8):");
        } while (choice < 0 || choice > 8);
        return choice;
    }

    // clubs that already exist when the program starts, had them added because stopping the program would remove all the clubs - better approach
    static void loadDefaultClubs() {
        clubs.add(new TechnologyClub("Google Developer Groups on Campus", "TBA", 50, "Mixed"));
        clubs.add(new TechnologyClub("Cybersecurity Club", "TBA", 50, "Python"));
        clubs.add(new SportsClub("Gaming Club", "TBA", 50, "Esports"));
        clubs.add(new SportsClub("Sports Club", "TBA", 50, "Football"));
        clubs.add(new ArtsClub("Anime Club", "TBA", 50, "Animation"));
        clubs.add(new ArtsClub("Music Club", "TBA", 50, "Music"));
    }

    // keeps asking until the user types a whole number
    static int readInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please type a whole number.");
            }
        }
    }

    static String readText(String message) {
        String text = "";
        while (text.isEmpty()) {
            System.out.println(message);
            text = input.nextLine().trim();
        }
        return text;
    }

    static Student findStudent(int id) throws NotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new NotFoundException("No student with ID " + id);
    }

    // shows all clubs (default and created) and returns the one picked by number
    static Club chooseClub() throws NotFoundException {
        if (clubs.isEmpty()) {
            throw new NotFoundException("There are no clubs yet");
        }
        for (int i = 0; i < clubs.size(); i++) {
            Club c = clubs.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " (" + c.getCategory() + ", "
                    + c.getMemberCount() + " members)");
        }
        int number = readInt("Club number:");
        if (number < 1 || number > clubs.size()) {
            throw new NotFoundException("No club with number " + number);
        }
        return clubs.get(number - 1);
    }

    // event numbers start at 1, so the list position is number - 1 - user should know event number when checking in or can see available events from selecting option 6
    static Event findEvent(int id) throws NotFoundException {
        if (id < 1 || id > events.size()) {
            throw new NotFoundException("No event with number " + id);
        }
        return events.get(id - 1);
    }

    // simple check that the date looks like dd-mm-yyyy
    static void checkDate(String date) {
        String[] parts = date.split("-");
        if (parts.length != 3 || parts[2].length() != 4) {
            throw new IllegalArgumentException("Date must look like 20-11-2026");
        }
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            Integer.parseInt(parts[2]);
            if (month < 1 || month > 12 || day < 1 || day > 31) {
                throw new IllegalArgumentException("Day must be 1-31 and month 1-12");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Date must look like 20-11-2026");
        }
    }

    public static void RegisterStudent() {
        int id = readInt("Student ID:");
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID must be positive");
            }
            for (Student s : students) {
                if (s.getId() == id) {
                    throw new DuplicateException("A student with ID " + id + " already exists");
                }
            }
            String name = readText("Name:");
            String major = readText("Major:");
            students.add(new Student(id, name, major));
            System.out.println(name + " registered.");
        } catch (DuplicateException | IllegalArgumentException e) {
            System.out.println("Could not register: " + e.getMessage());
        }
    }

    public static void CreateClub() {
        String name = readText("Club name:");
        try {
            for (Club c : clubs) {
                if (c.getName().equalsIgnoreCase(name)) {
                    throw new DuplicateException("That club already exists");
                }
            }
            System.out.println("1. Technology  2. Sports  3. Arts  4. Volunteer");
            int type = readInt("Club type (1-4):");
            if (type < 1 || type > 4) {
                throw new IllegalArgumentException("Club type must be between 1 and 4");
            }
            String advisor = readText("Advisor:");
            int max = readInt("Maximum members:");
            if (max <= 0) {
                throw new IllegalArgumentException("Maximum members must be more than 0");
            }

            if (type == 1) {
                clubs.add(new TechnologyClub(name, advisor, max, readText("Main programming language:")));
            } else if (type == 2) {
                clubs.add(new SportsClub(name, advisor, max, readText("Sport:")));
            } else if (type == 3) {
                clubs.add(new ArtsClub(name, advisor, max, readText("Art form:")));
            } else {
                clubs.add(new VolunteerClub(name, advisor, max, readInt("Required volunteer hours:")));
            }
            System.out.println("Club created.");
        } catch (DuplicateException | IllegalArgumentException e) {
            System.out.println("Could not create club: " + e.getMessage());
        }
    }

    // changes one detail of a club chosen from the list
    public static void EditClub() {
        try {
            Club club = chooseClub();
            System.out.println("1. Name  2. Advisor  3. Maximum members  4. " + club.getActivityLabel() + "  0. Cancel");
            int option = readInt("What do you want to change?");
            if (option == 0) {
                System.out.println("Nothing changed.");
                return;
            } else if (option == 1) {
                String name = readText("New name:");
                for (Club c : clubs) {
                    if (c != club && c.getName().equalsIgnoreCase(name)) {
                        throw new DuplicateException("That club already exists");
                    }
                }
                club.setName(name);
            } else if (option == 2) {
                club.setAdvisor(readText("New advisor:"));
            } else if (option == 3) {
                club.setMaxMembers(readInt("New maximum members:"));
            } else if (option == 4) {
                club.setActivityInfo(readText("New " + club.getActivityLabel() + ":"));
            } else {
                throw new IllegalArgumentException("Pick a number from 0 to 4");
            }
            System.out.println("Club updated: " + club.getSummary());
        } catch (NotFoundException | DuplicateException | IllegalArgumentException e) {
            System.out.println("Could not edit club: " + e.getMessage());
        }
    }

    public static void CreateEvent() {
        try {
            Club club = chooseClub();
            String name = readText("Event name:");
            String date = readText("Date (dd-mm-yyyy):");
            checkDate(date);
            String venue = readText("Venue:");
            int capacity = readInt("Capacity:");
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be more than 0");
            }
            Event e = new Event(events.size() + 1, name, date, venue, capacity, club);
            events.add(e);
            System.out.println("Event created with number " + e.getId());
        } catch (NotFoundException | IllegalArgumentException e) {
            System.out.println("Could not create event: " + e.getMessage());
        }
    }

    public static void RegisterStudentForEvent() {
        try {
            Student s = findStudent(readInt("Student ID:"));
            Event e = findEvent(readInt("Event number:"));
            e.register(s);
            System.out.println(s.getName() + " is now registered for " + e.getName());
        } catch (NotFoundException | EventFullException | DuplicateException e) {
            System.out.println("Could not register: " + e.getMessage());
        }
    }

    public static void ViewParticipationReports() {
        if (events.isEmpty()) {
            System.out.println("No events yet.");
            return;
        }
        System.out.println("=== Events ===");
        for (Event e : events) {
            System.out.println(e.getSummary());
        }
        System.out.println("=== Clubs ===");
        for (Club c : clubs) {
            System.out.println(c.getSummary());
        }
        System.out.println("=== Students ===");
        for (Student s : students) {
            System.out.println(s.getName() + " (" + s.getMajor() + ") went to "
                    + s.getHistory().size() + " event(s)");
        }
    }

    // Campus Passport: a stamp for each club type the student attended an event of - Expo passport
    public static void NewExtraFunctionality() {
        try {
            Student s = findStudent(readInt("Student ID:"));
            System.out.println(s.getName() + "'s Campus Passport");
            int count = 0;
            for (String cat : categories) {
                if (s.getStamps().contains(cat)) {
                    System.out.println("  [X] " + cat);
                    count++;
                } else {
                    System.out.println("  [ ] " + cat);
                }
            }
            if (count == categories.length) {
                System.out.println("Full Circle! You have a stamp from every club type.");
                return;
            }
            System.out.println("Events that would give you a new stamp:");
            boolean found = false;
            for (Event e : events) {
                String cat = e.getClub().getCategory();
                if (!s.getStamps().contains(cat) && !e.isFull()) {
                    System.out.println("  Event #" + e.getId() + " " + e.getName() + " (" + cat + ")");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("  None open right now.");
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // lets a student join a club from the list
    public static void BonusFunctionality() {
        try {
            Student s = findStudent(readInt("Student ID:"));
            System.out.println("Available clubs:");
            Club c = chooseClub();
            c.addMember(s);
            System.out.println(s.getName() + " joined " + c.getName());
        } catch (NotFoundException | ClubFullException | DuplicateException e) {
            System.out.println("Could not join: " + e.getMessage());
        }
    }
}
