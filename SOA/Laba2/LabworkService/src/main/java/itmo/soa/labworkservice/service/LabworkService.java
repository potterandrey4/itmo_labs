package itmo.soa.labworkservice.service;

import itmo.soa.labworkservice.dto.LabWorkInput;
import itmo.soa.labworkservice.dto.PageLabWork;
import itmo.soa.labworkservice.dto.SearchDTO;
import itmo.soa.labworkservice.model.Coordinates;
import itmo.soa.labworkservice.model.Discipline;
import itmo.soa.labworkservice.model.Difficulty;
import itmo.soa.labworkservice.model.LabWork;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LabworkService {
	private static final Map<String, Comparator<LabWork>> SORT_COMPARATORS;

	static {
		Map<String, Comparator<LabWork>> comparators = new ConcurrentHashMap<>();
		comparators.put("id", Comparator.comparing(LabWork::getId, Comparator.nullsLast(Long::compareTo)));
		comparators.put("name", Comparator.comparing(LabWork::getName, Comparator.nullsLast(String::compareTo)));
		comparators.put("minimalpoint",
				Comparator.comparing(LabWork::getMinimalPoint, Comparator.nullsLast(Double::compareTo)));
		comparators.put("personalqualitiesmaximum",
				Comparator.comparing(LabWork::getPersonalQualitiesMaximum, Comparator.nullsLast(Integer::compareTo)));
		comparators.put("difficulty",
				Comparator.comparing(lab -> lab.getDifficulty() == null ? null : lab.getDifficulty().ordinal(),
						Comparator.nullsLast(Integer::compareTo)));
		comparators.put("creationdate",
				Comparator.comparing(LabWork::getCreationDate, Comparator.nullsLast(OffsetDateTime::compareTo)));
		comparators.put("discipline",
				Comparator.comparing(LabWork::getDisciplineId, Comparator.nullsLast(Long::compareTo)));
		comparators.put("disciplinename", comparators.get("discipline"));
		comparators.put("coordinates.x",
				Comparator.comparing(LabworkService::coordinatesX, Comparator.nullsLast(Double::compareTo)));
		comparators.put("coordinates.y",
				Comparator.comparing(LabworkService::coordinatesY, Comparator.nullsLast(Double::compareTo)));
		comparators.put("x", comparators.get("coordinates.x"));
		comparators.put("y", comparators.get("coordinates.y"));
		SORT_COMPARATORS = Collections.unmodifiableMap(comparators);
	}

	private final Map<Long, LabWork> storage = new ConcurrentHashMap<>();
	private final Map<Long, Discipline> disciplineStorage = new ConcurrentHashMap<>();
	private final AtomicLong idSequence = new AtomicLong(0);
	private final AtomicLong disciplineIdSequence = new AtomicLong(0);

	public PageLabWork searchLabWorks(SearchDTO filters) {
		SearchDTO safeFilters = filters == null ? new SearchDTO() : filters;
		int page = safeFilters.getPage() == null ? 0 : safeFilters.getPage();
		int size = safeFilters.getSize() == null ? 10 : safeFilters.getSize();
		page = Math.max(page, 0);
		size = Math.max(size, 1);

		Comparator<LabWork> comparator = buildComparator(safeFilters.getSort());
		List<LabWork> filtered = storage.values()
				.stream()
				.filter(lab -> filterByName(lab, safeFilters.getName()))
				.filter(lab -> filterByMinDifficulty(lab, safeFilters.getMinDifficulty()))
				.filter(lab -> filterByMinimalPoint(lab, safeFilters.getMinimalPointGreaterThan()))
				.filter(lab -> filterByPersonalQualities(lab, safeFilters.getPersonalQualitiesMaximumGreaterThan()))
				.filter(lab -> filterByCoordinateGreater(lab, safeFilters.getXGreaterThan(),
						safeFilters.getYGreaterThan()))
				.filter(lab -> filterByCoordinateLess(lab, safeFilters.getXLessThan(), safeFilters.getYLessThan()))
				.filter(lab -> filterByCreationDate(lab, safeFilters.getCreationDateFrom(),
						safeFilters.getCreationDateTo()))
				.filter(lab -> filterByDisciplineName(lab, safeFilters.getDisciplineName()))
				.sorted(comparator)
				.toList();

		int total = filtered.size();
		if (total == 0) {
			return PageLabWork.of(Collections.emptyList(), 0, page, size);
		}

		int fromIndex = Math.min(page * size, total);
		int toIndex = Math.min(fromIndex + size, total);
		List<LabWork> pageContent = filtered.subList(fromIndex, toIndex)
				.stream()
				.map(this::copy)
				.toList();
		return PageLabWork.of(pageContent, total, page, size);
	}

	public LabWork createLabwork(LabWorkInput input) {
		LabWork labWork = new LabWork();
		applyInput(labWork, input);
		long id = idSequence.incrementAndGet();
		labWork.setId(id);
		labWork.setCreationDate(OffsetDateTime.now(ZoneOffset.UTC));
		storage.put(id, labWork);
		return copy(labWork);
	}

	public LabWork updateLabwork(long id, LabWorkInput input) {
		LabWork existing = storage.get(id);
		if (existing == null) {
			throw new NoSuchElementException("LabWork with id=" + id + " not found");
		}
		applyInput(existing, input);
		return copy(existing);
	}

	public LabWork getLabwork(long id) {
		LabWork labWork = storage.get(id);
		if (labWork == null) {
			throw new NoSuchElementException("LabWork with id=" + id + " not found");
		}
		return copy(labWork);
	}

	public void deleteLabwork(long id) {
		LabWork removed = storage.remove(id);
		if (removed == null) {
			throw new NoSuchElementException("LabWork with id=" + id + " not found");
		}
	}

	public Map<String, Long> groupByDiscipline() {
		return storage.values()
				.stream()
				.collect(Collectors.groupingBy(
						lab -> {
							Discipline discipline = disciplineStorage.get(lab.getDisciplineId());
							return discipline != null && StringUtils.hasText(discipline.getName())
									? discipline.getName()
									: "UNKNOWN";
						}, Collectors.counting()));
	}

	public List<LabWork> findByNamePrefix(String prefix) {
		if (!StringUtils.hasText(prefix)) {
			throw new IllegalArgumentException("prefix parameter must not be blank");
		}
		return storage.values()
				.stream()
				.filter(lab -> lab.getName() != null && lab.getName().startsWith(prefix))
				.map(this::copy)
				.toList();
	}

	public List<LabWork> findByDifficultyGreaterThan(Difficulty difficulty) {
		if (difficulty == null) {
			throw new IllegalArgumentException("difficulty parameter is required");
		}
		return storage.values()
				.stream()
				.filter(lab -> lab.getDifficulty() != null && lab.getDifficulty().isGreaterThan(difficulty))
				.map(this::copy)
				.toList();
	}

	private Comparator<LabWork> buildComparator(String sortParam) {
		if (!StringUtils.hasText(sortParam)) {
			return SORT_COMPARATORS.get("id");
		}
		String[] tokens = sortParam.split(";");
		Comparator<LabWork> comparator = null;
		for (String rawToken : tokens) {
			if (!StringUtils.hasText(rawToken)) {
				continue;
			}
			String[] parts = rawToken.trim().split(",");
			String field = parts[0].trim().toLowerCase(Locale.ROOT);
			String direction = parts.length > 1 ? parts[1].trim().toLowerCase(Locale.ROOT) : "asc";
			Comparator<LabWork> fieldComparator = SORT_COMPARATORS.get(field);
			if (fieldComparator == null) {
				throw new IllegalArgumentException("Unsupported sort field: " + field);
			}
			if ("desc".equals(direction)) {
				fieldComparator = fieldComparator.reversed();
			} else if (!"asc".equals(direction)) {
				throw new IllegalArgumentException("Unsupported sort direction: " + direction);
			}
			comparator = comparator == null ? fieldComparator : comparator.thenComparing(fieldComparator);
		}
		return comparator == null ? SORT_COMPARATORS.get("id") : comparator;
	}

	private boolean filterByName(LabWork labWork, String nameFilter) {
		if (!StringUtils.hasText(nameFilter)) {
			return true;
		}
		return labWork.getName().toLowerCase().contains(nameFilter.trim().toLowerCase());
	}

	private boolean filterByMinDifficulty(LabWork labWork, Difficulty minDifficulty) {
		if (minDifficulty == null) {
			return true;
		}
		Difficulty difficulty = labWork.getDifficulty();
		if (difficulty == null) {
			return false;
		}
		return difficulty == minDifficulty || difficulty.isGreaterThan(minDifficulty);
	}

	private boolean filterByMinimalPoint(LabWork labWork, Double minimalPointGreaterThan) {
		if (minimalPointGreaterThan == null) {
			return true;
		}
		Double minimalPoint = labWork.getMinimalPoint();
		return minimalPoint != null && minimalPoint > minimalPointGreaterThan;
	}

	private boolean filterByPersonalQualities(LabWork labWork, Integer personalQualitiesGreaterThan) {
		if (personalQualitiesGreaterThan == null) {
			return true;
		}
		Integer value = labWork.getPersonalQualitiesMaximum();
		return value != null && value > personalQualitiesGreaterThan;
	}

	private boolean filterByCoordinateGreater(LabWork labWork, Double xGreater, Double yGreater) {
		Coordinates coordinates = labWork.getCoordinates();
		if (coordinates == null) {
			return xGreater == null && yGreater == null;
		}
		boolean matchesX = xGreater == null || (coordinates.getX() != null && Double.parseDouble(coordinates.getX()) > xGreater);
		boolean matchesY = yGreater == null || (coordinates.getY() != null && Double.parseDouble(coordinates.getY()) > yGreater);
		return matchesX && matchesY;
	}

	private boolean filterByCoordinateLess(LabWork labWork, Double xLess, Double yLess) {
		Coordinates coordinates = labWork.getCoordinates();
		if (coordinates == null) {
			return xLess == null && yLess == null;
		}
		boolean matchesX = xLess == null || (coordinates.getX() != null && Double.parseDouble(coordinates.getX()) < xLess);
		boolean matchesY = yLess == null || (coordinates.getY() != null && Double.parseDouble(coordinates.getY()) < yLess);
		return matchesX && matchesY;
	}

	private boolean filterByCreationDate(LabWork labWork, OffsetDateTime from, OffsetDateTime to) {
		OffsetDateTime creationDate = labWork.getCreationDate();
		if (creationDate == null) {
			return from == null && to == null;
		}
		boolean matchesFrom = from == null || !creationDate.isBefore(from);
		boolean matchesTo = to == null || !creationDate.isAfter(to);
		return matchesFrom && matchesTo;
	}

	private boolean filterByDisciplineName(LabWork labWork, String disciplineName) {
		if (!StringUtils.hasText(disciplineName)) {
			return true;
		}
		Discipline discipline = disciplineStorage.get(labWork.getDisciplineId());
		if (discipline == null || !StringUtils.hasText(discipline.getName())) {
			return false;
		}
		return discipline.getName().toLowerCase().contains(disciplineName.trim().toLowerCase());
	}

	private void applyInput(LabWork target, LabWorkInput input) {
		target.setName(input.getName().trim());
		target.setCoordinates(copyCoordinates(input.getCoordinates()));
		target.setMinimalPoint(Double.parseDouble(input.getMinimalPoint()));
		target.setPersonalQualitiesMaximum(
				input.getPersonalQualitiesMaximum() != null ? Integer.parseInt(input.getPersonalQualitiesMaximum())
						: null);
		Difficulty difficulty = input.getDifficulty() == null ? Difficulty.VERY_EASY : input.getDifficulty();
		target.setDifficulty(difficulty);
		target.setDisciplineId(input.getDisciplineId());
	}

	private LabWork copy(LabWork source) {
		LabWork clone = new LabWork();
		clone.setId(source.getId());
		clone.setName(source.getName());
		clone.setCoordinates(copyCoordinates(source.getCoordinates()));
		clone.setCreationDate(source.getCreationDate());
		clone.setMinimalPoint(source.getMinimalPoint());
		clone.setPersonalQualitiesMaximum(source.getPersonalQualitiesMaximum());
		clone.setDifficulty(source.getDifficulty());
		clone.setDisciplineId(source.getDisciplineId());
		return clone;
	}

	private Coordinates copyCoordinates(Coordinates coordinates) {
		if (coordinates == null) {
			return null;
		}
		return new Coordinates(coordinates.getX(), coordinates.getY());
	}

	private static Double coordinatesX(LabWork labWork) {
		Coordinates coordinates = labWork.getCoordinates();
		return coordinates == null ? null : Double.parseDouble(coordinates.getX());
	}

	private static Double coordinatesY(LabWork labWork) {
		Coordinates coordinates = labWork.getCoordinates();
		return coordinates == null ? null : Double.parseDouble(coordinates.getY());
	}

	// Discipline methods
	public List<Discipline> getAllDisciplines() {
		return new ArrayList<>(disciplineStorage.values());
	}

	public Discipline getDiscipline(long id) {
		Discipline discipline = disciplineStorage.get(id);
		if (discipline == null) {
			throw new NoSuchElementException("Discipline with id=" + id + " not found");
		}
		return copyDiscipline(discipline);
	}

	public Discipline createDiscipline(String name, int practiceHours) {
		Discipline discipline = new Discipline(name, practiceHours);
		long id = disciplineIdSequence.incrementAndGet();
		discipline.setId(id);
		disciplineStorage.put(id, discipline);
		return copyDiscipline(discipline);
	}

	public Discipline updateDiscipline(long id, String name, int practiceHours) {
		Discipline existing = disciplineStorage.get(id);
		if (existing == null) {
			throw new NoSuchElementException("Discipline with id=" + id + " not found");
		}
		existing.setName(name);
		existing.setPracticeHours(practiceHours);
		return copyDiscipline(existing);
	}

	public void deleteDiscipline(long id) {
		if (disciplineStorage.remove(id) == null) {
			throw new NoSuchElementException("Discipline with id=" + id + " not found");
		}
		// Remove from labworks
		storage.values().forEach(lab -> {
			if (lab.getDisciplineId() == id) {
				// Maybe set to null or default
			}
		});
	}

	private Discipline copyDiscipline(Discipline source) {
		Discipline clone = new Discipline(source.getName(), source.getPracticeHours());
		clone.setId(source.getId());
		return clone;
	}
}
