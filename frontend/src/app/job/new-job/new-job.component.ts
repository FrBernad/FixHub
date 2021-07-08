import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-job',
  templateUrl: './new-job.component.html',
  styleUrls: ['./new-job.component.scss']
})
export class NewJobComponent implements OnInit {
  categories: string[] = ['CARPINTERO',
  'CATERING',
  'CHEF',
  'ELECTRICISTA',
  'ENTREGA',
  'FOTOGRAFO',
  'FUMIGADOR',
  'GASISTA',
  'HERRERO',
  'JARDINERO',
  'LIMPIEZA',
  'CUIDADOR_DE_ANCIANO',
  'MANTENIMIENTO',
  'MECANICO',
  'MUDANZA',
  'NINERA',
  'PASEADOR_DE_PERRO',
  'PLOMERO',
  'PINTOR',
  'TECHISTA',
  'VIDRIERO'];

  jobForm: FormGroup;

  maxImagesPerJob: number = 6;
  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;
  constructor() { }

  ngOnInit(): void {
    this.jobForm = new FormGroup({
      jobProvided: new FormControl(null,[Validators.required, Validators.maxLength(this.maxJobProvidedLength), Validators.pattern("^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      jobCategory: new FormControl(null, [Validators.required]),
      price: new FormControl(null, [Validators.required, Validators.min(this.minPrice), Validators.max(this.maxPrice)]),
      description: new FormControl(null, [Validators.required, Validators.maxLength(this.maxDescriptionLength)])
    })

  }

  onSubmit() {
    console.log(this.jobForm);
  }



}
