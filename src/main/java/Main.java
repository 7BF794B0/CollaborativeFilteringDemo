import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class Main
{
	private static ArrayList<String> names = new ArrayList<>();
	private static ArrayList<String> films = new ArrayList<>();
	private static ArrayList<Integer> ratings = new ArrayList<>();
	private static ArrayList<Person> persons = new ArrayList<>();

	/**
	 * https://en.wikipedia.org/wiki/Cosine_similarity
	 *
	 * @param p1 The user for which you want to calculate values
	 * @param p2 Users have the basis of which will count values
	 */
	private static void CosineMeasure (Person p1, Person p2)
	{
		int dotProduct = 0;
		for (int i = 0; i < p1.Ratings.size(); i++)
			dotProduct += p1.Ratings.get(i) * p2.Ratings.get(i);

		int sum = 0;
		for (int i = 0; i < p1.Ratings.size(); i++)
			sum += Math.pow(p1.Ratings.get(i), 2);
		double modulePersona1 = Math.sqrt(sum);

		sum = 0;
		for (int i = 0; i < p2.Ratings.size(); i++)
			sum += Math.pow(p2.Ratings.get(i), 2);
		double modulePersona2 = Math.sqrt(sum);

		double cosineMeasure = dotProduct / (modulePersona1 * modulePersona2);
		p1.setCosineMeasure(p2.getId() - 1, cosineMeasure);
	}

	public static void main (String[] args)
	{
		// Read the file
		try (BufferedReader br = new BufferedReader(new FileReader(new File("data.csv"))))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				// Split string into chunks
				String[] chunks = line.split(",");
				names.add(chunks[0]);
				films.add(chunks[1]);
				ratings.add(Integer.valueOf(chunks[2]));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// Group the data by the field "Name", all the objects stored in the collection
		String tempName = names.get(0);
		Person pers = new Person();
		for (int i = 0, id = 1; i < names.size(); i++)
		{
			// Checking the last element OR checking for a new person
			if (!names.get(i).equals(tempName))
			{
				pers.setId(id++);
				pers.setName(tempName);
				persons.add(pers);

				tempName = names.get(i);
				pers = new Person();
			}

			pers.Films.add(films.get(i));
			pers.Ratings.add(ratings.get(i));

			if (i == names.size() - 1)
			{
				pers.setId(id);
				pers.setName(tempName);
				persons.add(pers);
				tempName = names.get(i);
			}
		}

		// Initialize arrays for storage of cosine measure
		for (Person p : persons)
			p.setCosineMeasureArraySize(persons.size());

		for (int i = 0; i < persons.size(); i++)
		{
			for (int j = 0; j < persons.size(); j++)
			{
				if (i == j)
					continue;
				CosineMeasure(persons.get(i), persons.get(j));
			}
		}

		DBController controller = new DBController("jdbc:postgresql://192.168.164.132:5432/postgres", "postgres", "password");
		controller.Connect();
		for (Person p : persons)
			controller.Insert(new Gson().toJson(p));
	}
}
