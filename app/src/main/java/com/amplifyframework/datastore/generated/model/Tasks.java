package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Tasks type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks")
public final class Tasks implements Model {
  public static final QueryField ID = field("Tasks", "id");
  public static final QueryField TITLE = field("Tasks", "title");
  public static final QueryField BODY = field("Tasks", "body");
  public static final QueryField STATUS = field("Tasks", "status");
  public static final QueryField APART_OF = field("Tasks", "tasksApartOfId");
  public static final QueryField FILE_NAME = field("Tasks", "file_name");
  public static final QueryField LOCATION = field("Tasks", "location");
  public static final QueryField LOCATION_LAT = field("Tasks", "location_lat");
  public static final QueryField LOCATION_LON = field("Tasks", "location_lon");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String title;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="String") String status;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "tasksApartOfId", type = Team.class) Team apartOf;
  private final @ModelField(targetType="String") String file_name;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="String") String location_lat;
  private final @ModelField(targetType="String") String location_lon;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getStatus() {
      return status;
  }
  
  public Team getApartOf() {
      return apartOf;
  }
  
  public String getFileName() {
      return file_name;
  }
  
  public String getLocation() {
      return location;
  }
  
  public String getLocationLat() {
      return location_lat;
  }
  
  public String getLocationLon() {
      return location_lon;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Tasks(String id, String title, String body, String status, Team apartOf, String file_name, String location, String location_lat, String location_lon) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.status = status;
    this.apartOf = apartOf;
    this.file_name = file_name;
    this.location = location;
    this.location_lat = location_lat;
    this.location_lon = location_lon;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Tasks tasks = (Tasks) obj;
      return ObjectsCompat.equals(getId(), tasks.getId()) &&
              ObjectsCompat.equals(getTitle(), tasks.getTitle()) &&
              ObjectsCompat.equals(getBody(), tasks.getBody()) &&
              ObjectsCompat.equals(getStatus(), tasks.getStatus()) &&
              ObjectsCompat.equals(getApartOf(), tasks.getApartOf()) &&
              ObjectsCompat.equals(getFileName(), tasks.getFileName()) &&
              ObjectsCompat.equals(getLocation(), tasks.getLocation()) &&
              ObjectsCompat.equals(getLocationLat(), tasks.getLocationLat()) &&
              ObjectsCompat.equals(getLocationLon(), tasks.getLocationLon()) &&
              ObjectsCompat.equals(getCreatedAt(), tasks.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tasks.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getStatus())
      .append(getApartOf())
      .append(getFileName())
      .append(getLocation())
      .append(getLocationLat())
      .append(getLocationLon())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Tasks {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("apartOf=" + String.valueOf(getApartOf()) + ", ")
      .append("file_name=" + String.valueOf(getFileName()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("location_lat=" + String.valueOf(getLocationLat()) + ", ")
      .append("location_lon=" + String.valueOf(getLocationLon()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Tasks justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Tasks(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      status,
      apartOf,
      file_name,
      location,
      location_lat,
      location_lon);
  }
  public interface BuildStep {
    Tasks build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep title(String title);
    BuildStep body(String body);
    BuildStep status(String status);
    BuildStep apartOf(Team apartOf);
    BuildStep fileName(String fileName);
    BuildStep location(String location);
    BuildStep locationLat(String locationLat);
    BuildStep locationLon(String locationLon);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String title;
    private String body;
    private String status;
    private Team apartOf;
    private String file_name;
    private String location;
    private String location_lat;
    private String location_lon;
    @Override
     public Tasks build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Tasks(
          id,
          title,
          body,
          status,
          apartOf,
          file_name,
          location,
          location_lat,
          location_lon);
    }
    
    @Override
     public BuildStep title(String title) {
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep status(String status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep apartOf(Team apartOf) {
        this.apartOf = apartOf;
        return this;
    }
    
    @Override
     public BuildStep fileName(String fileName) {
        this.file_name = fileName;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep locationLat(String locationLat) {
        this.location_lat = locationLat;
        return this;
    }
    
    @Override
     public BuildStep locationLon(String locationLon) {
        this.location_lon = locationLon;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, String status, Team apartOf, String fileName, String location, String locationLat, String locationLon) {
      super.id(id);
      super.title(title)
        .body(body)
        .status(status)
        .apartOf(apartOf)
        .fileName(fileName)
        .location(location)
        .locationLat(locationLat)
        .locationLon(locationLon);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder status(String status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder apartOf(Team apartOf) {
      return (CopyOfBuilder) super.apartOf(apartOf);
    }
    
    @Override
     public CopyOfBuilder fileName(String fileName) {
      return (CopyOfBuilder) super.fileName(fileName);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder locationLat(String locationLat) {
      return (CopyOfBuilder) super.locationLat(locationLat);
    }
    
    @Override
     public CopyOfBuilder locationLon(String locationLon) {
      return (CopyOfBuilder) super.locationLon(locationLon);
    }
  }
  
}
