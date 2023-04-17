package fr.selquicode.mareu.data.DI;

import java.util.List;

import fr.selquicode.mareu.data.API.ApiService;
import fr.selquicode.mareu.data.API.SuperMeetingApiService;
import fr.selquicode.mareu.data.API.SuperMeetingGenerator;
import fr.selquicode.mareu.data.model.Meeting;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static ApiService service = new SuperMeetingApiService(SuperMeetingGenerator.SUPER_MEETINGS);

    /**
     * Get an instance on @{@link ApiService}
     * @return service, a list
     */
    public static ApiService getApiService() { return service; }

    /**
     * Get always a new instance on @{@link ApiService}. Useful for tests, so we ensure the context is clean.
     * @return new list of meetings
     */
    public static ApiService getNewInstanceApiService(List<Meeting> meetings){
        return new SuperMeetingApiService(meetings);
    }
}
